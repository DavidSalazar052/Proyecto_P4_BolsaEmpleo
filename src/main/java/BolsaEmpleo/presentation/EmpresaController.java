package BolsaEmpleo.presentation;

import BolsaEmpleo.logic.Base.Empresa;
import BolsaEmpleo.logic.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EmpresaController {
    @Autowired
    private Service service;

    @GetMapping("/DashboardEmpresa")
    public String mostrar_DashboardEmpresa() {
        return "presentation/Empresa/DashboardEmpresa";
    }

    @PostMapping("/create")
    public String crear_empresa(Model model, @ModelAttribute @Validated Empresa empresa, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("empresas", empresa);
            return "presentation/Empresa/viewRegistroEmpresa";
        }
        try {
            service.EmpresasAdd(empresa);
            return "presentation/Empresa/viewRegistroEmpresa";

        } catch (Exception e) {
            result.addError(new FieldError("prestamo", "id", empresa.getId(), false, null, null, "empresa ya existe"));
            model.addAttribute("editing", false);
            return "presentation/Empresa/viewRegistroEmpresa";
        }
    }
}


//LOGIN
    // root (/)
    //Get mapping no autoizado

//Empresa/Oferente/caracteristica
    //Get mapping List -> mostrar por id
    //PostMapping /search
    //GetMapping /show -> mostrar todos
    //GetMapping /create
    //GetMapping /edit{id}
    //GetMapping /delete{id}
    //GetMapping /copy{id}
    //Postmapping /update

/*
* package org.example.prestamoform.logic;

import org.example.prestamoform.data.sql.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class Service {
    @Autowired
    private PrestamoRepository prestamos;

     public List<Prestamo> prestamosAll() {
        return prestamos.findAll();
    }

    public List<Prestamo> prestamoSearch(String nombre) {
        return prestamos.findByNombre(nombre);
    }

    public void prestamosAdd(Prestamo prestamo) {
        if(prestamos.existsById(prestamo.getId())){
            throw new IllegalArgumentException("Prestamo ya existe");
        }
        prestamos.save(prestamo);
    }

    public Prestamo prestamoRead(String id) {
        return prestamos.findById(id).orElseThrow(() -> new IllegalArgumentException("Prestamo no existe"));
    }

    public void prestamoUpdate(Prestamo prestamo) {
        if(!prestamos.existsById(prestamo.getId())) {
            throw new IllegalArgumentException("Prestamo no existe");
        }
         prestamos.save(prestamo);
    }

    public void prestamoDelete(String id) {
         prestamos.deleteById(id);
    }

    public void prestamoCopy(String id) {
         prestamos.deleteById(id);
    }

}

*
*
*
* */



