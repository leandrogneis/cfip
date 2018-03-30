package edu.cfip.app.desktop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import edu.cfip.core.dao.springjpa.UsuarioRepositorio;
import edu.cfip.core.model.Usuario;
import edu.porgamdor.util.desktop.ambiente.FrmPerfil;
import edu.porgamdor.util.desktop.ss.SSMensagem;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FrmUsuario extends FrmPerfil {
	@Autowired
	private UsuarioRepositorio dao;

	public FrmUsuario() {
		super.confirmar(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirmarAction();
			}
		});
	}

	public void confirmarAction() {
		try {
			super.validarFormulario();
			boolean existe = false;
			if (dao.findFistByLogin(perfil.getLogin()) != null) {
				existe = true;
			}
			if (dao.findFistByEmail(perfil.getEmail()) != null) {
				existe = true;
			}
			if (existe)
				SSMensagem.avisa(
						"O usuário " + perfil.getLogin() + " ou E-mail " + perfil.getEmail() + " Já está cadastrado");
			else {
				Usuario usuario = (Usuario) perfil;
				dao.save(usuario);
				prosseguir();
			}
		} catch (Exception e) {
			SSMensagem.avisa(e.getMessage());
		}

	}
}
