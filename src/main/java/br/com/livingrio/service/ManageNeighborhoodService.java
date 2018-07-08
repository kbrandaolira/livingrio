package br.com.livingrio.service;

import java.text.Normalizer;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.livingrio.dao.impl.CityDAO;
import br.com.livingrio.dao.impl.NeighborhoodDAO;
import br.com.livingrio.dao.impl.RegionDAO;
import br.com.livingrio.exception.InvalidDataException;
import br.com.livingrio.model.Neighborhood;
import br.com.livingrio.utils.LivingIOUtils;
import br.com.livingrio.web.params.NeighborhoodModel;

@Service
public class ManageNeighborhoodService {

	private ManageFileNeighborhoodService manageFileNeighborhoodService;
	
	private NeighborhoodDAO neighborhoodDAO;

	private CityDAO cityDAO;
	
	private RegionDAO regionDAO;
	
	@Autowired
	public ManageNeighborhoodService( NeighborhoodDAO neighborhoodDAO, CityDAO cityDAO, RegionDAO regionDAO, ManageFileNeighborhoodService manageFileNeighborhoodService ){
		this.neighborhoodDAO = neighborhoodDAO;
		this.cityDAO = cityDAO;
		this.regionDAO = regionDAO;
		this.manageFileNeighborhoodService = manageFileNeighborhoodService;
		
	}
	
	
	/**
	 * @throws InvalidDataException caso haja dados inválidos vindos do formulário
	 * @param NeighborhoodModel com os dados preenchidos do formulário
	*/
	@Transactional
	public void execute(NeighborhoodModel model) throws InvalidDataException {
		this.validate(model);

		Neighborhood neighborhood = null;
		
		if( model.getId() == null ){
			neighborhood = new Neighborhood();
		} else {
			neighborhood = this.neighborhoodDAO.find(model.getId());
			
		}
		
		neighborhood.setCity( cityDAO.find( model.getCityId() ) );
		
		if (model.getRegionId() != null && model.getRegionId() > 0){
		
			neighborhood.setRegion( regionDAO.find( model.getRegionId() ) );
			
		}
		
		neighborhood.setName( model.getName().toUpperCase() );
		
		neighborhood.setDescription( model.getDescription() );
		
		//Removendo espaços em branco e usando o toLowerCase()
		String url = model.getName().toLowerCase().replaceAll("\\s", "");
		//Removendo os acentos
		url = Normalizer.normalize( url, Normalizer.Form.NFD );
		url = url.replaceAll( "[^\\p{ASCII}]", "" );
		
		neighborhood.setUrl( url );

		if( model.getId() == null ){
			neighborhoodDAO.create( neighborhood );
		} else {
			neighborhoodDAO.update( neighborhood );
		}

		this.manageFileNeighborhoodService.execute( neighborhood, model.getMultipartFiles() );
		
	}
	
	private void validate(NeighborhoodModel model) throws InvalidDataException {
		StringBuilder msg = new StringBuilder();
		
		boolean hasState = true;
		boolean hasCity = true;
		boolean hasRegion = true;
		boolean hasNeighborhood = true;
		
		if( model.getStateId() != null && model.getStateId().equals(0L) ){
			msg.append("Estado é um campo obrigatório.");
			
			hasState = false;
		}
		
		if( model.getCityId() != null && model.getCityId().equals(0L) ){
			if( msg.length() > 0 ){
				msg.append("<br>");
			}
			msg.append("Cidade é um campo obrigatório.");
			
			hasCity = false;
		}
		
	
		if( model.getName() != null && model.getName().isEmpty() ){
			if( msg.length() > 0 ){
				msg.append("<br>");
			}
			msg.append("Nome do Bairro é um campo obrigatório");
			
			hasNeighborhood = false;
		}
		
		if( model.getDescription() == null || model.getDescription().isEmpty() ){
			if( msg.length() > 0 ){
				msg.append("<br>");
			}
			msg.append("Descrição é um campo obrigatório");
		}
		
		if( model.getId() == null && hasState && hasCity && hasRegion && hasNeighborhood ){
			if( this.neighborhoodDAO.getBy(model.getStateId(), model.getCityId(), model.getRegionId(), model.getName().toUpperCase()) != null ){
				if( msg.length() > 0 ){
					msg.append("<br>");
				}
				
				
				msg.append("Bairro já se encontra cadastrado.");
				
			}
			
			
		}
		
		
		if( model.getMultipartFiles() != null ){
			for( int i = 0; i < model.getMultipartFiles().length; i++ ){
				String type = LivingIOUtils.getType(model.getMultipartFiles()[i].getContentType());
				System.out.println(type);
				
				/*Quando não é adicionada nenhuma imagem no <input type="file"/> vem um arquivo temporário com o ContentType application/octet-stream.
				 * Foi feito um código não elegante para resolver o problema
				 */
				//TODO Resolver o código não elegante abaixo
				if( type.equals(".octet-stream") ){
					model.setMultipartFiles(null);
					break;
				}
				
				if( msg.length() > 0 ){
					msg.append("<br>");
				}
				
				if( !(type.equals(".jpg") || type.equals(".jpeg") || type.equals(".png") || type.equals(".bmp")) ) {
					msg.append("<br>Formato inválido da foto. São aceitos os formatos: JPG, JPEG, PNG e BMP.");
				}
				
				
				
			}
			
		}
		
		if( msg.length() > 0 ){
			throw new InvalidDataException( msg.toString() );
		}
		
	}
	
}
