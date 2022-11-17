package cz.czechitas.java2webapps.lekce8.controller;

import cz.czechitas.java2webapps.lekce8.repository.OsobaRepository;
import cz.czechitas.java2webapps.lekce8.entity.Osoba;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;

@Controller
public class OsobaController {
  private final OsobaRepository osobaRepository;

  public OsobaController(OsobaRepository osobaRepository) {
    this.osobaRepository = osobaRepository;
  }

  @InitBinder
  public void nullStringBinding(WebDataBinder binder) {
    //prázdné textové řetězce nahradit null hodnotou
    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
  }

  @GetMapping("/")
  public Object seznam() {
    //TODO načíst seznam osob
    return new ModelAndView("seznam")
            .addObject("osoby", osobaRepository.findAll());
  }

  @GetMapping("/novy")
  public Object novy() {
    return new ModelAndView("detail")
            .addObject("osoba", new Osoba());
  }

  @PostMapping("/novy")
  public Object pridat(@ModelAttribute("osoba") @Valid Osoba osoba, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "detail";
    }
    osobaRepository.save(osoba);
    return "redirect:/";
  }

  @GetMapping("/{id:[0-9]+}")
  public Object detail(@PathVariable long id) {
    //TODO načíst údaj o osobě
    return new ModelAndView("detail")
            .addObject("osoba", osobaRepository.findById(2L).get());
  }

  @PostMapping("/{id:[0-9]+}")
  public Object ulozit(@PathVariable long id, @ModelAttribute("osoba") @Valid Osoba osoba, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "detail";
    }
    //TODO uložit údaj o osobě
    osoba.setId(id);
    osobaRepository.save(osoba);
    return "redirect:/";
  }

  @PostMapping(value = "/{id:[0-9]+}", params = "akce=smazat")
  public Object smazat(@PathVariable long id) {
    //TODO smazat údaj o osobě
    osobaRepository.deleteById(id);
    return "redirect:/";
  }

}
