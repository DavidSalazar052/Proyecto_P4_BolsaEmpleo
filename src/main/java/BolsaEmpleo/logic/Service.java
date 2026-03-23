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
    public void CaracteristicasAdd(Caracteristicas caracteristicas){
        if(Carac_Repo.existsById(caracteristicas.getId())){
            throw new IllegalArgumentException("Ya existe esta caracteristica");
        }
    }

//ENTIDADES CON FK ---
    //--PUESTO--
    public List<Puesto> findAll_puesto_emp(){
        return Puesto_Repo.findAll();
    }

    public void Puesto_emp_Add(Puesto puestoEmp){
        if(Puesto_Repo.existsById(puestoEmp.getId())){
            throw new IllegalArgumentException("El puesto para esta empresa ya existe");
        }
    }


    public List<Puesto> Top5_PuestosRecientes(){
        List<Puesto> result;
        result = Puesto_Repo.findTop5Puestos();
        if (result == null) {
            throw new IllegalArgumentException("No hay suficientes puestos para mostrar");
        }
        return result; // con uno ya basta
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
    public List<PuestoHabilidades> findAll_puestos_Selected(){
       // List<Caracteristicas> seleccionadas = null;
       // return Puesto_hab_Repo.buscarPorIds(seleccionadas);
        return null;
    }

    //--OFERENTE HABILIDADES--
    public List<OferenteHabilidades> findAll_Oferente_hab(){
        return Oferente_hab_Repo.findAll();
    }


}
