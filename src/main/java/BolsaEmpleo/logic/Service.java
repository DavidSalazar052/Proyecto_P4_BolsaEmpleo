package BolsaEmpleo.logic;

import BolsaEmpleo.data.*;

import BolsaEmpleo.logic.Base.*;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
@org.springframework.stereotype.Service
public class Service {

    // ENTIDADES BASE
    @Autowired private UsuarioRepository usuario_Repo;
    @Autowired private AdministradorRepository Admi_Repo;
    @Autowired private EmpresaRepository Emp_Repo;
    @Autowired private OferentesRepository Ofe_Repo;
    @Autowired private CaracteristicasRepository Carac_Repo;

    // ENTIDADES CON FK
    @Autowired private PuestoRepository Puesto_Repo;
    @Autowired private PuestoHabilidadRepository Puesto_hab_Repo;
    @Autowired private OferenteHabilidadRepository Oferente_hab_Repo;


    // -- USUARIOS --
    public Usuario Usuario_Login(String username, String clave) {
        return usuario_Repo.findByUsername(username, clave);
    }


    // -- ADMINISTRADORES --
    public List<Administrador> findAll_Administradores() {
        return Admi_Repo.findAll();
    }

    public void AdministradorAdd(Administrador admi) {
        Admi_Repo.save(admi);
    }


    // -- EMPRESAS --
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


    // -- OFERENTES --
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


    // -- CARACTERISTICAS --
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


    // -- PUESTO --
    public List<Puesto> findAll_puesto_emp() {
        return Puesto_Repo.findAll();
    }

    public void Puesto_emp_Add(Puesto puestoEmp) {
        Puesto_Repo.save(puestoEmp);
    }

    public void Puesto_emp_delete(Integer id) {
        Puesto_Repo.deleteById(id);
    }

    public List<Puesto> Top5_PuestosRecientes() {
        List<Puesto> result = Puesto_Repo.findTop5Puestos();
        if (result == null || result.isEmpty()) {
            throw new IllegalArgumentException("No hay suficientes puestos para mostrar");
        }
        return result;
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


    // -- PUESTO HABILIDADES --
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

    public List<PuestoHabilidades> findBySkill(String skill) {
        List<PuestoHabilidades> puestos = Puesto_hab_Repo.findAll();
        puestos.removeIf(p -> !p.getHabilidad().getNombre().equals(skill));
        return puestos;
    }


    // -- OFERENTE HABILIDADES --
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
