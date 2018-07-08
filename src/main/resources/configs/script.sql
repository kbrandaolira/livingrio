-- Script Migração Estrutura nova

insert into Criterion  values (9,'Depoimento geral sobre o bairro',null,null,'Depoimento',now());

INSERT INTO Post (comment,grade,criterion_id,person_id,createdDate) 
	SELECT ec.comment,ec.grade,ec.criterion_id,e.person_id,e.date 
    FROM EvaluationCriterion ec INNER JOIN Evaluation e on ec.evaluation_id = e.id
    WHERE ec.comment <> '';


INSERT INTO Post (comment,criterion_id,person_id,createdDate) 
	SELECT e.comment,9,e.person_id,e.date 
    FROM Evaluation e
    WHERE e.comment <> '';
