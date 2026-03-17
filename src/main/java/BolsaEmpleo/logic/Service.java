package BolsaEmpleo.logic;

import BolsaEmpleo.data.AdministradorRepository;
import BolsaEmpleo.data.EmpresaRepository;
import BolsaEmpleo.data.CaracteristicasRepository;
import BolsaEmpleo.data.OferentesRepository;
import BolsaEmpleo.data.PuestoEmpresaRepository;
import BolsaEmpleo.data.PuestoHabilidadRepository;

import BolsaEmpleo.logic.Base.Administrador;
import BolsaEmpleo.logic.Base.Caracteristicas;
import BolsaEmpleo.logic.Base.Empresa;
import BolsaEmpleo.logic.Base.Oferente;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
@org.springframework.stereotype.Service
public class Service {
    @Autowired
    private AdministradorRepository Admi_Repo;
    @Autowired
    private EmpresaRepository Emp_Repo;
    @Autowired
    private OferentesRepository Ofe_Repo;
    @Autowired
    private CaracteristicasRepository Carac_Repo;
     @Autowired
    private PuestoEmpresaRepository Puesto_emp_Repo;
      @Autowired
    private PuestoHabilidadRepository Puesto_hab_Repo; 

//ENTIDADES PRINCIPALES

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

        //--PUESTO EMPRESA--
    public List<Puesto> findAll_puesto_emp(){
        return Puesto_emp_Repo.findAll();
    }

    public void Puesto_emp_Add(Puesto puestoEmp){
        if(Puesto_emp_Repo.existsById(puestoEmp.getId())){
            throw new IllegalArgumentException("El puesto para esta empresa ya existe");
        }
    }

            //--PUESTO HABILIDAD--
    public List<PuestoHabilidades> findAll_puesto_hab(){
        return Puesto_hab_Repo.findAll();
    }

    public void Puesto_hab_Add(PuestoHabilidades puestoEmp){
        if(Puesto_hab_Repo.existsById(puestoEmp.getId())){
            throw new IllegalArgumentException("Esta habilidad ya esta asociada al puesto");
        }
    }

}
