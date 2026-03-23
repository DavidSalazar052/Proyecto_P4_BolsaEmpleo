package BolsaEmpleo.logic;

import BolsaEmpleo.data.*;

import BolsaEmpleo.logic.Base.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
@org.springframework.stereotype.Service
public class Service {
    //ENTIDADES BASES

    @Autowired
    private UsuarioRepository usuario_Repo;
    @Autowired
    private AdministradorRepository Admi_Repo;
    @Autowired
    private EmpresaRepository Emp_Repo;
    @Autowired
    private OferentesRepository Ofe_Repo;
    @Autowired
    private CaracteristicasRepository Carac_Repo;

    //ENTIDADES CON FK
    @Autowired
    private PuestoRepository Puesto_Repo;
    @Autowired
    private PuestoHabilidadRepository Puesto_hab_Repo;
    @Autowired
    private OferenteHabilidadRepository Oferente_hab_Repo;



    //--USUARIOS --
    public Usuario Usuario_Login(String username, String clave){
        Usuario result = usuario_Repo.findByUsername(username,clave);
        return result;
    }

    //--ADMINISTRADORES--
    public List<Administrador> findAll_Administradores(){
        return Admi_Repo.findAll();
    }
    public void AdministradorAdd(Administrador Admi){
        if(Admi_Repo.existsById(Admi.getId())){
            throw new IllegalArgumentException("Este ID ya esta registrado");
        }
    }



    //--EMPRESAS--
    public List<Empresa> findAll_Empresas(){
        return Emp_Repo.findAll();
    }
    public void EmpresasAdd(Empresa Emp){
        if(Emp_Repo.existsById(Emp.getId())){
            throw new IllegalArgumentException("Este Empresa ya esta registrada con este ID");
        }
    }
    public List<Empresa> empresaSearchByNombre(String nombre) {
        return Emp_Repo.findByNombre(nombre);
    }

    public Empresa empresaRead(String id) {
        return Emp_Repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Prestamo no existe"));
    }
    public void empresaUpdate(Empresa empresa) {
        if(!Emp_Repo.existsById(empresa.getId())) {
            throw new IllegalArgumentException("Prestamo no existe");
        }
        Emp_Repo.save(empresa);
    }
    public void empresaDelete(String id) {
        Emp_Repo.deleteById(id);
    }
    public void empresaCopy(String id) {
        Emp_Repo.deleteById(id);
    }

    //--OFERENTES--
    public List<Oferente> findAll_Oferentes(){
        return Ofe_Repo.findAll();
    }
    public void OferentesAdd(Oferente Oferente){
        if(Ofe_Repo.existsById(Oferente.getId())){
            throw new IllegalArgumentException("Un Oferente ya esta registrado con esta ID");
        }
    }

    //--CARACTERISTICAS--
    public List<Caracteristicas> findAll_Caracteristicas(){
        return Carac_Repo.findAll();
    }
    public void caracteristicasAdd(Caracteristicas Emp){
        if(Emp_Repo.existsById(Emp.getId())){
            throw new IllegalArgumentException("Este Oferente ya esta registrado con este ID");
        }
        Carac_Repo.save(Emp);
    }
    public List<Caracteristicas> findPadresCaracteristicas() {
        return Carac_Repo.findPadres();
    }

    public List<Caracteristicas> findHijosCaracteristicas(String padreId) {
        return Carac_Repo.findHijos(padreId);
    }
    public Caracteristicas caracteristicasRead(String id) {
        return Carac_Repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Prestamo no existe"));
    }
    public void caracteristicasUpdate(Caracteristicas caracteristicas) {
        if(!Carac_Repo.existsById(caracteristicas.getId())) {
            throw new IllegalArgumentException("Prestamo no existe");
        }
        Carac_Repo.save(caracteristicas);
    }
    public void prestamoDelete(String id) {
        Carac_Repo.deleteById(id);
    }

    public void prestamoCopy(String id) {
        Carac_Repo.deleteById(id);
    }

//----------------------------------------------------------------------------------------
    //--PUESTO--
    public List<Puesto> findAll_puesto_emp(){
        return Puesto_Repo.findAll();
    }

    public void Puesto_emp_Add(Puesto puestoEmp){
        if(Puesto_Repo.existsById(puestoEmp.getId())){
            throw new IllegalArgumentException("El puesto para esta empresa ya existe");
        }
    }

    public void Puesto_emp_delete(Puesto puestoEmp){
        if(Puesto_Repo.existsById(puestoEmp.getId())){
            throw new IllegalArgumentException("Este Empresa ya esta registrada con este ID");
        }
        Puesto_Repo.deleteById(puestoEmp.getId());
    }

    public List<Puesto> Top5_PuestosRecientes(){
        List<Puesto> result;
        result = Puesto_Repo.findTop5Puestos();
        if (result == null) {
            throw new IllegalArgumentException("No hay suficientes puestos para mostrar");
        }
        return result; // con uno ya basta
    }

    public Puesto PuestoRead(String id) {
        return Puesto_Repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Prestamo no existe"));
    }

    public void PuestoUpdate(Puesto puesto) {
        if(!Puesto_Repo.existsById(puesto.getId())) {
            throw new IllegalArgumentException("Prestamo no existe");
        }
        Puesto_Repo.save(puesto);
    }

    public void PuestoDelete(String id) {
        Puesto_Repo.deleteById(id);
    }
    public void PuestoCopy(String id) {
        Puesto_Repo.deleteById(id);
    }


    //--PUESTO HABILIDADES--
    public List<PuestoHabilidades> findAll_puesto_hab(){
        return Puesto_hab_Repo.findAll();
    }

    public void Puesto_hab_Add(PuestoHabilidades puestoEmp){
        if(Puesto_hab_Repo.existsById(puestoEmp.getId())){
            throw new IllegalArgumentException("Esta habilidad ya esta asociada al puesto");
        }
    }

    public void Puesto_hab_delete(PuestoHabilidades puestoEmp){
        if(Puesto_hab_Repo.existsById(puestoEmp.getId())){
            throw new IllegalArgumentException("Esta habilidad ya esta asociada al puesto");
        }

        Puesto_hab_Repo.deleteById(puestoEmp.getId());
    }
    public PuestoHabilidades prestamoRead(String id) {
        return Puesto_hab_Repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Prestamo no existe"));
    }
    //--OFERENTE HABILIDADES--
    public List<OferenteHabilidades> findAll_Oferente_hab(){
        return Oferente_hab_Repo.findAll();
    }
    public void Ofere_hab_Add(OferenteHabilidades oferenteHab){
        if(Oferente_hab_Repo.existsById(oferenteHab.getId())){
            throw new IllegalArgumentException("Este Oferente ya esta registrado con este ID");
        }
        Oferente_hab_Repo.save(oferenteHab);
    }
    public void Oferente_hab_Delete(String id) {
        Oferente_hab_Repo.deleteById(id);
    }

    


}
