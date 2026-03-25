package BolsaEmpleo.logic;

import BolsaEmpleo.data.*;
import BolsaEmpleo.logic.Base.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@org.springframework.stereotype.Service
public class Service {

    // ── Repositorios ─────────────────────────────────────────────
    @Autowired private UsuarioRepository           usuario_Repo;
    @Autowired private AdministradorRepository     Admi_Repo;
    @Autowired private EmpresaRepository           Emp_Repo;
    @Autowired private OferentesRepository         Ofe_Repo;
    @Autowired private CaracteristicasRepository   Carac_Repo;
    @Autowired private PuestoRepository            Puesto_Repo;
    @Autowired private PuestoHabilidadRepository   Puesto_hab_Repo;
    @Autowired private OferenteHabilidadRepository Oferente_hab_Repo;


    // ══════════════════════════════════════════════════════════════
    //  USUARIOS
    // ══════════════════════════════════════════════════════════════

    public Usuario Usuario_Login(String username, String clave) {
        return usuario_Repo.findByUsername(username, clave);
    }


    // ══════════════════════════════════════════════════════════════
    //  ADMINISTRADORES
    // ══════════════════════════════════════════════════════════════

    public List<Administrador> findAll_Administradores() {
        return Admi_Repo.findAll();
    }

    public void AdministradorAdd(Administrador admi) {
        Admi_Repo.save(admi);
    }

    public void aprobarEmpresa(Integer id) {
        Empresa empresa = Emp_Repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Empresa no encontrada: " + id));
        empresa.setAprobada(true);
        Emp_Repo.save(empresa);
    }

    public void aprobarOferente(Integer id) {
        Oferente oferente = Ofe_Repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Oferente no encontrado: " + id));
        oferente.setAprobado(true);
        Ofe_Repo.save(oferente);
    }


    // ══════════════════════════════════════════════════════════════
    //  EMPRESAS
    // ══════════════════════════════════════════════════════════════

    public List<Empresa> findAll_Empresas() {
        return Emp_Repo.findAll();
    }

    public List<Empresa> findAll_EmpresasNoAprobadas() {
        return Emp_Repo.findAllByNoAprobada();
    }

    public List<Empresa> findAll_EmpresasAprobadas() {
        return Emp_Repo.findAllByAprobada();
    }

    public void EmpresasAdd(Empresa emp) {
        Emp_Repo.save(emp);
    }

    public List<Empresa> empresaSearchByNombre(String nombre) {
        return Emp_Repo.findByNombre(nombre);
    }

    public Empresa empresaRead(Integer id) {
        return Emp_Repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Empresa no existe"));
    }

    /** Devuelve la Empresa asociada al id del Usuario logueado. */
    public Empresa empresaByUsuario(Integer usuarioId) {
        Empresa emp = Emp_Repo.findByUsuarioId(usuarioId);
        if (emp == null) throw new IllegalArgumentException("No se encontró empresa para el usuario: " + usuarioId);
        return emp;
    }

    public void empresaUpdate(Empresa empresa) {
        if (!Emp_Repo.existsById(empresa.getId())) {
            throw new IllegalArgumentException("Empresa no existe");
        }
        Emp_Repo.save(empresa);
    }

    public void empresaDelete(Integer id) {
        Emp_Repo.deleteById(id);
    }

    public void registrarEmpresa(Usuario usuario, Empresa empresa) {
        if (usuario_Repo.findByUsernameOnly(usuario.getUsername()) != null) {
            throw new IllegalArgumentException("El nombre de usuario '" + usuario.getUsername() + "' ya está en uso.");
        }
        usuario_Repo.save(usuario);
        Emp_Repo.save(empresa);
    }


    // ══════════════════════════════════════════════════════════════
    //  OFERENTES
    // ══════════════════════════════════════════════════════════════

    public List<Oferente> findAll_Oferentes() {
        return Ofe_Repo.findAll();
    }

    public void OferentesAdd(Oferente oferente) {
        Ofe_Repo.save(oferente);
    }

    public List<Oferente> findAll_Oferentes_NoAprobadas() {
        return Ofe_Repo.findAllByNoAprobadaOferente();
    }

    public List<Oferente> findAll_Oferentes_Aprobadas() {
        return Ofe_Repo.findAllByAprobadaOferente();
    }

    public void registrarOferente(Usuario usuario, Oferente oferente) {
        if (usuario_Repo.findByUsernameOnly(usuario.getUsername()) != null) {
            throw new IllegalArgumentException("El nombre de usuario '" + usuario.getUsername() + "' ya está en uso.");
        }
        usuario_Repo.save(usuario);
        Ofe_Repo.save(oferente);
    }


    // ══════════════════════════════════════════════════════════════
    //  CARACTERÍSTICAS
    // ══════════════════════════════════════════════════════════════

    public List<Caracteristicas> findAll_Caracteristicas() {
        return Carac_Repo.findAll();
    }

    public void caracteristicasAdd(Caracteristicas carac) {
        Carac_Repo.save(carac);
    }

    public List<Caracteristicas> findPadresCaracteristicas() {
        return Carac_Repo.findPadres();
    }

    public List<Caracteristicas> findHijosCaracteristicas(Integer padreId) {
        return Carac_Repo.findHijos(padreId);
    }

    public Caracteristicas caracteristicasRead(Integer id) {
        return Carac_Repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Caracteristica no existe"));
    }

    public void caracteristicasUpdate(Caracteristicas caracteristicas) {
        if (!Carac_Repo.existsById(caracteristicas.getId())) {
            throw new IllegalArgumentException("Caracteristica no existe");
        }
        Carac_Repo.save(caracteristicas);
    }

    public void caracteristicasDelete(Integer id) {
        Carac_Repo.deleteById(id);
    }

    /**
     * Crea una característica nueva.
     * padreId == null o 0 → raíz (sin padre).
     */
    public void crearCaracteristica(String nombre, Integer padreId) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre de la característica no puede estar vacío.");
        }
        Caracteristicas nueva = new Caracteristicas();
        nueva.setNombre(nombre.trim());
        if (padreId != null && padreId > 0) {
            Caracteristicas padre = Carac_Repo.findById(padreId)
                    .orElseThrow(() -> new IllegalArgumentException("La categoría padre no existe."));
            nueva.setPadre(padre);
        }
        Carac_Repo.save(nueva);
    }


    // ══════════════════════════════════════════════════════════════
    //  PUESTOS
    // ══════════════════════════════════════════════════════════════

    public List<Puesto> findAll_puesto_emp() {
        return Puesto_Repo.findAll();
    }

    /**
     * Top 5 puestos públicos — devuelve lista vacía si no hay ninguno
     * para que la vista muestre el mensaje apropiado.
     */
    public List<Puesto> Top5_PuestosRecientes() {
        List<Puesto> result = Puesto_Repo.findTop5Puestos();
        return result != null ? result : Collections.emptyList();
    }

    /** Todos los puestos de una empresa concreta. */
    public List<Puesto> puestosByEmpresa(Integer empresaId) {
        return Puesto_Repo.findByEmpresaId(empresaId);
    }

    public void Puesto_emp_Add(Puesto puestoEmp) {
        Puesto_Repo.save(puestoEmp);
    }

    /**
     * Crea un puesto nuevo para la empresa logueada y lo persiste.
     */
    public void crearPuesto(Empresa empresa, String descripcion, Integer salario, String tipo) {
        Puesto p = new Puesto();
        p.setEmpresa(empresa);
        p.setDescripcion(descripcion);
        p.setSalario(salario);
        p.setTipo(tipo.toUpperCase());   // "PUBLICO" | "PRIVADO"
        p.setEstado("ACTIVO");
        p.setFecha(java.time.LocalDate.now().toString());
        Puesto_Repo.save(p);
    }

    /** Agrega una característica requerida a un puesto. */
    public void agregarHabilidadPuesto(Integer puestoId, Integer caracteristicaId, Integer nivel) {
        Puesto puesto = Puesto_Repo.findById(puestoId)
                .orElseThrow(() -> new IllegalArgumentException("Puesto no existe"));
        Caracteristicas carac = Carac_Repo.findById(caracteristicaId)
                .orElseThrow(() -> new IllegalArgumentException("Característica no existe"));
        PuestoHabilidades ph = new PuestoHabilidades();
        ph.setPuesto(puesto);
        ph.setHabilidad(carac);
        ph.setNivel(nivel);
        Puesto_hab_Repo.save(ph);
    }

    /** Desactiva un puesto: ACTIVO → INACTIVO. */
    public void desactivarPuesto(Integer puestoId) {
        Puesto p = Puesto_Repo.findById(puestoId)
                .orElseThrow(() -> new IllegalArgumentException("Puesto no existe"));
        p.setEstado("INACTIVO");
        Puesto_Repo.save(p);
    }

    public Puesto PuestoRead(Integer id) {
        return Puesto_Repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Puesto no existe"));
    }

    public void PuestoUpdate(Puesto puesto) {
        if (!Puesto_Repo.existsById(puesto.getId())) {
            throw new IllegalArgumentException("Puesto no existe");
        }
        Puesto_Repo.save(puesto);
    }

    public void PuestoDelete(Integer id) {
        Puesto_Repo.deleteById(id);
    }


    // ══════════════════════════════════════════════════════════════
    //  BÚSQUEDA DE PUESTOS POR CARACTERÍSTICAS (página pública)
    // ══════════════════════════════════════════════════════════════

    /**
     * Devuelve puestos PÚBLICOS y ACTIVOS que contengan al menos una
     * de las características seleccionadas. Lista vacía si ids es vacío.
     */
    public List<Puesto> buscarPuestosPorCaracteristicas(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) return Collections.emptyList();
        return Puesto_Repo.findPuestosPublicosByCaracteristicas(ids);
    }


    // ══════════════════════════════════════════════════════════════
    //  COINCIDENCIA OFERENTE ↔ PUESTO
    // ══════════════════════════════════════════════════════════════

    /**
     * DTO que encapsula el resultado de comparar un oferente con un puesto.
     * porcentaje = (requisitos cumplidos / total requeridos) × 100
     */
    public static class ResultadoCandidato {
        public final Oferente oferente;
        public final int      requisitosTotal;
        public final int      requisitosCumplidos;
        public final double   porcentaje;

        public ResultadoCandidato(Oferente oferente, int total, int cumplidos) {
            this.oferente             = oferente;
            this.requisitosTotal      = total;
            this.requisitosCumplidos  = cumplidos;
            this.porcentaje           = total > 0 ? (cumplidos * 100.0 / total) : 0.0;
        }
    }

    /**
     * Para un puesto dado, recorre todos los oferentes aprobados y calcula
     * cuántos requisitos cumple cada uno (nivel oferente >= nivel requerido).
     * Devuelve la lista ordenada de mayor a menor coincidencia.
     */
    public List<ResultadoCandidato> calcularCandidatos(Integer puestoId) {
        List<PuestoHabilidades> requeridas = Puesto_hab_Repo.findByPuestoId(puestoId);
        int total = requeridas.size();

        List<Oferente> oferentes = Ofe_Repo.findAllByAprobadaOferente();
        List<ResultadoCandidato> resultados = new ArrayList<>();

        for (Oferente oferente : oferentes) {
            List<OferenteHabilidades> habilidadesOferente =
                    Oferente_hab_Repo.findByOferenteId(oferente.getId());

            // Índice: caracteristicaId → nivel que tiene el oferente
            Map<Integer, Integer> nivelOferente = new HashMap<>();
            for (OferenteHabilidades oh : habilidadesOferente) {
                nivelOferente.put(oh.getCaracteristicas().getId(), oh.getNivel());
            }

            int cumplidos = 0;
            for (PuestoHabilidades req : requeridas) {
                Integer nivelTiene = nivelOferente.get(req.getHabilidad().getId());
                if (nivelTiene != null && nivelTiene >= req.getNivel()) {
                    cumplidos++;
                }
            }

            resultados.add(new ResultadoCandidato(oferente, total, cumplidos));
        }

        resultados.sort((a, b) -> Double.compare(b.porcentaje, a.porcentaje));
        return resultados;
    }


    // ══════════════════════════════════════════════════════════════
    //  PUESTO HABILIDADES
    // ══════════════════════════════════════════════════════════════

    public List<PuestoHabilidades> findAll_puesto_hab() {
        return Puesto_hab_Repo.findAll();
    }

    public void Puesto_hab_Add(PuestoHabilidades puestoHab) {
        Puesto_hab_Repo.save(puestoHab);
    }

    public void Puesto_hab_delete(Integer id) {
        Puesto_hab_Repo.deleteById(id);
    }

    public PuestoHabilidades PuestoHabilidadRead(Integer id) {
        return Puesto_hab_Repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("PuestoHabilidad no existe"));
    }

    public List<PuestoHabilidades> habilidadesByPuesto(Integer puestoId) {
        return Puesto_hab_Repo.findByPuestoId(puestoId);
    }

    public List<PuestoHabilidades> findBySkill(String skill) {
        List<PuestoHabilidades> puestos = Puesto_hab_Repo.findAll();
        puestos.removeIf(p -> !p.getHabilidad().getNombre().equals(skill));
        return puestos;
    }


    // ══════════════════════════════════════════════════════════════
    //  OFERENTE HABILIDADES
    // ══════════════════════════════════════════════════════════════

    public List<OferenteHabilidades> findAll_Oferente_hab() {
        return Oferente_hab_Repo.findAll();
    }

    public void Ofere_hab_Add(OferenteHabilidades oferenteHab) {
        Oferente_hab_Repo.save(oferenteHab);
    }

    public void Oferente_hab_Delete(Integer id) {
        Oferente_hab_Repo.deleteById(id);
    }
}