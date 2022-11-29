package Moviecollection.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import Moviecollection.domain.GenreRepository;
import Moviecollection.domain.Movie;
import Moviecollection.domain.MovieRepository;

@Controller
public class MovieController {
	
	@Autowired
	private MovieRepository repository;
	
	@Autowired
	private GenreRepository grepository;
	
	@RequestMapping(value = {"/", "movielist"})
	public String movieList(Model model) {
		model.addAttribute("movies", repository.findAll());
		return "movielist";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/add")
	public String addMovie(Model model) {
		model.addAttribute("newMovie", new Movie());
		model.addAttribute("genres", grepository.findAll());
		return "addMovie";
	}
	
	@PostMapping("saveMovie")
	public String saveMovie(Movie movie) {
		repository.save(movie);
		return "redirect:movielist";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
	public String deleteMovie(@PathVariable("id") Long id, Model model) {
		repository.deleteById(id);
		return "redirect:../movielist";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("edit/{id}")
	public String editMovie(@PathVariable("id") Long id, Model model) {
		model.addAttribute("editMovie", repository.findById(id));
		model.addAttribute("genres", grepository.findAll());
		return "editMovie";
	}
	
}
