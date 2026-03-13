package BolsaEmpleo.logic;

import BolsaEmpleo.data.AdministradorRepository;
import BolsaEmpleo.data.EmpresaRepository;
import BolsaEmpleo.data.entity.Administrador;
import BolsaEmpleo.data.entity.Empresa;
import BolsaEmpleo.data.entity.Oferente;
import BolsaEmpleo.data.OferentesRepository;
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
}
