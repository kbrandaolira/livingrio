package br.com.livingrio.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.livingrio.dao.GenericDAO;
import br.com.livingrio.model.NeighborhoodCriterion;
import br.com.livingrio.web.params.CriterionModel;
import br.com.livingrio.web.params.NeighborhoodModel;

@Repository
public class NeighborhoodCriterionDAO extends GenericDAO<NeighborhoodCriterion, Serializable>{

	/**
	 * @return Um List<NeighborhoodModel> dos bairros mais bem avaliados, média geral, por Estado, Cidade e Região.
	*/
	public List<NeighborhoodModel> top( int numberOfTop, Long idState, Long idCity, Long idRegion ){
		
		StringBuilder queryBldr = new StringBuilder("select new br.com.livingrio.web.params.NeighborhoodModel(n.id, s.url, c.url, r.url, n.url, n.name, s.acronym, c.name, r.name, (sum(nc.overall)/count(nc.neighborhood.id)) ) "
				+ " from NeighborhoodCriterion nc INNER JOIN nc.neighborhood n "
				+ " INNER JOIN n.city c INNER JOIN c.state s LEFT JOIN n.region r ");
		
		int cont = 0;
		
		if( (idState != null && idState != 0L) || (idCity != null && idCity != 0L) || (idRegion != null && idRegion != 0L) ){
			queryBldr.append( " WHERE " );
		}
		
		if( idState != null && idState != 0L ){
			queryBldr.append( " s.id = :idState " );
			
			cont++;
		}

		if( idCity != null && idCity != 0L ){
			if( cont > 0 ){
				queryBldr.append( " AND " );
			}
			
			queryBldr.append( " c.id = :idCity " );
			
			cont++;
		}
		
		if( idRegion != null && idRegion != 0L ){
			if( cont > 0 ){
				queryBldr.append( " AND " );
			}
			
			queryBldr.append( " r.id = :idRegion " );
			
			cont++;
		}
		
		queryBldr.append( " GROUP BY n.id, s.url, c.url, r.url, n.url, n.name, s.acronym, c.name, r.name "
				+ "ORDER BY (sum(nc.overall)/count(nc.neighborhood.id)) DESC " );
		
		Query query = super.entityManager.createQuery( queryBldr.toString() );
		
		if( idState != null && idState != 0L ){
			query.setParameter("idState", idState);
		}

		if( idCity != null && idCity != 0L ){
			query.setParameter("idCity", idCity);
		}
		
		if( idRegion != null && idRegion != 0L ){
			query.setParameter("idRegion", idRegion);
		}
		
		return (List<NeighborhoodModel>) query.setMaxResults(numberOfTop).getResultList();
		
	}
	
	/**
	 * @return Um CriterionModel preenchido a partir dos parâmetros idCriterion, idState, idCity, idRegion
	*/
	public CriterionModel getCriterionModelBy( Long idCriterion, Long idNeighborhood ){
		
		StringBuilder queryBldr = new StringBuilder("select new br.com.livingrio.web.params.CriterionModel(crit.id, crit.name, nc.overall, crit.iconLocation) "
				+ " from NeighborhoodCriterion nc INNER JOIN nc.neighborhood n INNER JOIN nc.criterion crit " );
		
		int cont = 0;
		
		if( (idCriterion != null && idCriterion != 0L) || (idNeighborhood != null && idNeighborhood != 0L) ){
			queryBldr.append( " WHERE " );
		}
		
		if( idCriterion != null && idCriterion != 0L ){
			queryBldr.append(" crit.id = :idCriterion ");
			
			cont++;
		}
		
		if( idNeighborhood != null && idCriterion != 0L ){
			if( cont > 0 ){
				queryBldr.append( " AND " );
				
			}

			queryBldr.append( " n.id = :idNeighborhood " );
			
			cont++;
		}
		
		queryBldr.append( " GROUP BY crit.id, crit.name, nc.overall, crit.iconLocation " );
		
		Query query = super.entityManager.createQuery( queryBldr.toString() );
		
		if( idCriterion != null && idCriterion != 0L ){
			query.setParameter("idCriterion", idCriterion);
		}
		
		if( idNeighborhood != null && idNeighborhood != 0L ){
			query.setParameter("idNeighborhood", idNeighborhood);
		}
		
		return (CriterionModel) query.setMaxResults(1).getResultList().get(0);
		
		
	}
	
	
}
