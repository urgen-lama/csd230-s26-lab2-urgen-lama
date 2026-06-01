package csd230.s26.lab1.controllers;

import csd230.s26.lab1.entities.CartEntity;
import csd230.s26.lab1.entities.MagazineEntity;
import csd230.s26.lab1.repositories.CartRepository;
import csd230.s26.lab1.repositories.MagazineRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MagazineController {

    private final MagazineRepository magazineRepository;
    private final CartRepository cartRepository;

    public MagazineController(MagazineRepository magazineRepository, CartRepository cartRepository) {
        this.magazineRepository = magazineRepository;
        this.cartRepository = cartRepository;
    }

    @GetMapping("/magazines")
    public String listMagazines(Model model) {
        model.addAttribute("magazines", magazineRepository.findAll());
        return "magazineList";
    }

    @GetMapping("/magazines/add")
    public String showAddForm(Model model) {
        model.addAttribute("magazine", new MagazineEntity());
        return "magazineForm";
    }

    @PostMapping("/magazines/save")
    public String saveMagazine(@ModelAttribute MagazineEntity magazine) {
        magazineRepository.save(magazine);
        return "redirect:/magazines";
    }

    @GetMapping("/magazines/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        MagazineEntity magazine = magazineRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid magazine id: " + id));
        model.addAttribute("magazine", magazine);
        return "magazineForm";
    }

    @GetMapping("/magazines/delete/{id}")
    public String deleteMagazine(@PathVariable Long id) {
        MagazineEntity magazine = magazineRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid magazine id: " + id));
        for (CartEntity cart : cartRepository.findAll()) {
            cart.getProducts().remove(magazine);
            cartRepository.save(cart);
        }
        magazineRepository.delete(magazine);
        return "redirect:/magazines";
    }

    @GetMapping("/magazines/addToCart/{id}")
    public String addToCart(@PathVariable Long id) {
        MagazineEntity magazine = magazineRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid magazine id: " + id));
        CartEntity cart = cartRepository.findById(1L)
                .orElseGet(() -> cartRepository.save(new CartEntity()));
        cart.getProducts().add(magazine);
        cartRepository.save(cart);
        return "redirect:/magazines";
    }
}