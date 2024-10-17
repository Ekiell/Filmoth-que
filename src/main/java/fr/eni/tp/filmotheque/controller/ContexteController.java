package fr.eni.tp.filmotheque.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import fr.eni.tp.filmotheque.bll.contexte.ContexteService;
import fr.eni.tp.filmotheque.bo.Membre;

@Controller
@RequestMapping("/contexte")
@SessionAttributes({ "membreEnSession" })
public class ContexteController {
	
	private ContexteService service;
	
	public ContexteController(ContexteService service) {
		System.out.println("Ajout membre en session");
		this.service = service;
	}

	@ModelAttribute("membreEnSession")
	public Membre membreEnSession() {
		System.out.println("Add Attribut Session");
		return new Membre();
	}
	
	@GetMapping("/session")
	public String connexion(
			@ModelAttribute("membreEnSession") Membre membreEnSession,
			@RequestParam(name = "email", required = false, defaultValue = "jtrillard@campus-eni.fr") String email) {
		Membre aCharger = service.charger(email);
		if (aCharger != null) {
			membreEnSession.setId(aCharger.getId());
			membreEnSession.setNom(aCharger.getNom());
			membreEnSession.setPrenom(aCharger.getPrenom());
			membreEnSession.setPseudo(aCharger.getPseudo());
			membreEnSession.setAdmin(aCharger.isAdmin());

			
		} else {
			membreEnSession.setId(0);
			membreEnSession.setNom(null);
			membreEnSession.setPrenom(null);
			membreEnSession.setPseudo(null);
			membreEnSession.setAdmin(false);
		}
		System.out.println(membreEnSession);
		return "redirect:/films";

	}
	
	@GetMapping("/cloture")
	private String finSesssion(SessionStatus status) {
		status.setComplete();
		return "redirect:/films";

	}
	
	@GetMapping
	public String choixContexte() {
		return "Contexte/view-contexte";
	}
}
