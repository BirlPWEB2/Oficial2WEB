package br.com.oficial.web.caninofeliz.bean;

import java.io.Serializable;


/**
 *  Funcionalidade não implementada
 */

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import br.com.oficial.web.caninofeliz.domain.Pessoa;
@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class VerificaSessaoBean implements Serializable{
	
	public boolean verificaSessao(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession(true);
        Pessoa user = (Pessoa) session.getAttribute("usuario");
        
        
        if(session.isNew() || user == null){
            return false;
        } else {
            return true;
	}

}
}
