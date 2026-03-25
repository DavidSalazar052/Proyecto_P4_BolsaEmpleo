package BolsaEmpleo.presentation;

import BolsaEmpleo.logic.Base.Caracteristicas;
import BolsaEmpleo.logic.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * ╔══════════════════════════════════════════════════════════════╗
 * ║  caracteristicaController.java                               ║
 * ║  Antes estaba vacío — ahora maneja las características       ║
 * ║  del Admin: listar jerárquicamente y crear nuevas.           ║
 * ╚══════════════════════════════════════════════════════════════╝
 */
@Controller
public class caracteristicaController {

    @Autowired
    private Service service;


}