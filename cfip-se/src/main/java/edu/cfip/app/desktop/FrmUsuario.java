package edu.cfip.app.desktop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import edu.cfip.core.dao.Repositorio;
import edu.porgamdor.util.desktop.ambiente.FrmPerfil;
import edu.porgamdor.util.desktop.ss.SSMensagem;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FrmUsuario extends FrmPerfil {
	@Autowired
	private Repositorio dao;
	public FrmUsuario() {
		super.confirmar(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirmarAction();
			}
		});
	}
	private void confirmarAction() {
		/*if (perfil.getId() == null) {
			boolean cadastrar = true;
			// isso porque estamos usando SpringDataJPA
			if (dao.findFistByLogin(perfil.getLogin()) != null) {
				cadastrar = false;
			}
			if (dao.findFistByEmail(perfil.getEmail()) != null) {
				cadastrar = false;
			}
			if (cadastrar) {
				dao.save(perfil);
				SSMensagem.informa("Usuario registrado com sucesso\nAcesse o sistema");
			} else
				SSMensagem.avisa(
						"O usuário " + perfil.getLogin() + " ou " + perfil.getEmail() + " Já está cadastrado");

		} else {
			dao.save(perfil);
			SSMensagem.informa("Dados alterados com sucesso");
		}*/
		dao.incluir(perfil);
		SSMensagem.informa("Usuario registrado com sucesso\nAcesse o sistema");
	}
}
