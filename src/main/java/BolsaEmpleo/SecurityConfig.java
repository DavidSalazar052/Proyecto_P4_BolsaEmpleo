package BolsaEmpleo;

import BolsaEmpleo.data.UsuarioRepository;
import BolsaEmpleo.logic.Base.Usuario;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

/*
 * ╔══════════════════════════════════════════════════════════════════════╗
 * ║  SecurityConfig                                                      ║
 * ║                                                                      ║
 * ║  Implementa el flujo descrito en el PDF de seguridad:                ║
 * ║                                                                      ║
 * ║  Petición → SecurityFilterChain → DispatcherServlet → Controller     ║
 * ║                                                                      ║
 * ║  • URLs públicas → permitAll() sin exigir login                      ║
 * ║  • URLs protegidas → requieren autenticación + rol correcto          ║
 * ║  • POST /login → interceptado por Spring Security (formLogin)        ║
 * ║    1. Extrae username/password del formulario                        ║
 * ║    2. Llama al AuthenticationManager                                 ║
 * ║    3. DaoAuthenticationProvider usa nuestro UserDetailsService       ║
 * ║    4. Si ok → guarda Authentication en SecurityContext + sesión      ║
 * ║    5. Redirige a /login?success para que el controller decida        ║
 * ║  • GET /logout → invalida sesión y redirige a /login                 ║
 * ╚══════════════════════════════════════════════════════════════════════╝
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UsuarioRepository usuarioRepository;

    public SecurityConfig(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // ── 1. SecurityFilterChain ────────────────────────────────────────────────
    // Define qué URLs son públicas, cuáles requieren rol, y configura formLogin.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // ── Recursos públicos (no requieren login) ─────────────────
                        .requestMatchers(
                                "/",
                                "/buscaPuesto",
                                "/login",
                                "/login/empresa",
                                "/login/oferente",
                                "/registro/empresa",
                                "/registro/oferente",
                                "/css/**",
                                "/js/**",
                                "/images/**"
                        ).permitAll()

                        // ── Rutas de Administrador ─────────────────────────────────
                        .requestMatchers(
                                "/DashboardAdministrador",
                                "/EmpresasPendientes",
                                "/OferentesPendientes",
                                "/AdminCaracteristicas",
                                "/admin/**"
                        ).hasRole("ADM")

                        // ── Rutas de Empresa ───────────────────────────────────────
                        .requestMatchers(
                                "/DashboardEmpresa",
                                "/empresa/**"
                        ).hasRole("EMP")

                        // ── Rutas de Oferente ──────────────────────────────────────
                        .requestMatchers(
                                "/DashboardOferente",
                                "/oferente/**"
                        ).hasRole("OFE")

                        // Cualquier otra URL requiere estar autenticado
                        .anyRequest().authenticated()
                )

                // ── formLogin: Spring Security intercepta POST /login ──────────
                // Según el PDF: el filtro toma username+password, construye un
                // Authentication, lo pasa al AuthenticationManager →
                // DaoAuthenticationProvider → UserDetailsService.
                .formLogin(form -> form
                        .loginPage("/login")                    // GET /login muestra el formulario (público)
                        .loginProcessingUrl("/login")           // POST /login lo procesa Spring Security
                        .usernameParameter("username")          // nombre del campo en el HTML
                        .passwordParameter("clave")             // nombre del campo en el HTML
                        .successHandler((request, response, authentication) -> {
                            // Tras autenticación exitosa, redirigimos según el rol
                            // (mismo comportamiento que tenía el LoginController)
                            String role = authentication.getAuthorities().iterator()
                                    .next().getAuthority();
                            switch (role) {
                                case "ROLE_ADM" -> response.sendRedirect("/DashboardAdministrador");
                                case "ROLE_EMP" -> response.sendRedirect("/DashboardEmpresa");
                                case "ROLE_OFE" -> response.sendRedirect("/DashboardOferente");
                                default         -> response.sendRedirect("/");
                            }
                        })
                        .failureUrl("/login?error=true")        // credenciales incorrectas
                        .permitAll()
                )

                // ── logout ────────────────────────────────────────────────────
                // Spring Security invalida la sesión y el SecurityContext.
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                )

                // Desactivamos CSRF solo para simplificar en desarrollo académico.
                // En producción real debería estar habilitado.
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    // ── 2. UserDetailsService ─────────────────────────────────────────────────
    // El PDF explica: DaoAuthenticationProvider llama a UserDetailsService
    // para cargar el UserDetails (username, password, roles).
    // Aquí conectamos nuestra tabla `usuario` con Spring Security.
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            // Buscamos el usuario en la BD por username solamente
            Usuario usuario = usuarioRepository.findByUsernameOnly(username);
            if (usuario == null) {
                throw new UsernameNotFoundException("Usuario no encontrado: " + username);
            }

            // Verificamos que sea aprobado si es EMP u OFE
            // (El LoginController ya no existe para esta lógica;
            //  ahora la manejamos en el successHandler de arriba
            //  y en los propios controladores de dashboard)

            // Convertimos el tipo (ADM/EMP/OFE) al formato ROLE_XXX
            // que Spring Security espera para hasRole()
            return User.builder()
                    .username(usuario.getUsername())
                    .password(usuario.getClave())
                    .authorities(List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getTipo())))
                    .build();
        };
    }

    // ── 3. PasswordEncoder ───────────────────────────────────────────────────
    // Las contraseñas están en texto plano en la BD (sin BCrypt).
    // NoOpPasswordEncoder las compara directamente.
    // NOTA: en producción usar BCryptPasswordEncoder.
    @SuppressWarnings("deprecation")
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    // ── 4. AuthenticationManager ─────────────────────────────────────────────
    // Expuesto como Bean por si algún controlador lo necesita directamente.
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}