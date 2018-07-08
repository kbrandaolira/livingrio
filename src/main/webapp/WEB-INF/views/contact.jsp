<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<head><title>Fale Conosco</title></head>

<html>

	<div class="row">
	    <div class="col-lg-12">
	        <h1 class="page-header">Fale Conosco
	        </h1>
	    </div>
	</div>
	
	<c:if test="${success != null}">
		<div class="row">
			<div class="col-md-12">
				<div class="alert alert-success" role="alert">${success}</div>
			</div>
		</div>
	</c:if>

	<c:if test="${error != null}">	
		<div class="row">
			<div class="col-md-12">
				<div class="alert alert-danger" role="alert">${error}</div>
			</div>
		</div>
	</c:if>
	
	<div class="row">
	    <div class="col-md-8">
	        <form:form action="contact" modelAttribute="contact" method="post" id="contactForm">
	            <div class="control-group form-group">
	                <div class="controls">
	                    <label>Nome:</label>
	                    <input type="text" name="name" class="form-control" id="name" required data-validation-required-message="Por favor digite o seu nome.">
	                    <p class="help-block"></p>
	                </div>
	            </div>
	            <div class="control-group form-group">
	                <div class="controls">
	                    <label>Email:</label>
	                    <input type="email" name="email" class="form-control" id="email" required data-validation-required-message="Por favor digite o seu e-mail.">
	                </div>
	            </div>
	            <div class="control-group form-group">
	                <div class="controls">
	                    <label>Mensagem:</label>
	                    <textarea name="message" rows="10" cols="100" class="form-control" id="message" required data-validation-required-message="Por favor digite a sua mensagem." maxlength="999" style="resize:none"></textarea>
	                </div>
	            </div>
	            <div id="success"></div>
	            <!-- For success/fail messages -->
	            <button type="submit" class="btn btn-primary">Enviar</button>
	        </form:form>
	    </div>
	
	</div>

</html>