package br.com.livingrio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.livingrio.service.PrepareRankingService;
import br.com.livingrio.web.params.NeighborhoodModel;

@Controller
public class RankingController {

	private PrepareRankingService prepareRankingService;
	
	@Autowired
	public RankingController( PrepareRankingService prepareRankingService ){
		this.prepareRankingService = prepareRankingService;
		
	}
	
	@RequestMapping( value = "/ranking", method = RequestMethod.GET )
	public String init(){
		return "ranking";
		
	}
	
	@RequestMapping( value = "/ranking/search", method = RequestMethod.GET )
	public @ResponseBody List<NeighborhoodModel> search( @RequestParam Long idState, @RequestParam Long idCity, @RequestParam Long idRegion){
		return (List<NeighborhoodModel>) this.prepareRankingService.execute(idState, idCity, idRegion);
		
	}
	
}
