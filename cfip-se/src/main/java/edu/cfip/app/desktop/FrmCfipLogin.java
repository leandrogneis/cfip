package edu.cfip.app.desktop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import edu.cfip.app.spring.SpringDesktopApp;
import edu.cfip.core.dao.springjpa.UsuarioRepositorio;
import edu.cfip.core.model.Usuario;
import edu.porgamdor.util.desktop.ambiente.FrmLogin;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FrmCfipLogin extends FrmLogin {
	@Autowired
	private UsuarioRepositorio repositorio;

	public FrmCfipLogin() {
		super.logar(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logarAction();
			}
		});
	}

	private void logarAction() {
		try {
			Usuario perfil = repositorio.login(getLogin(), getSenhaMD5());
			MDICfip mdi = SpringDesktopApp.getBean(MDICfip.class);
			FrmUsuario frm = SpringDesktopApp.getBean(FrmUsuario.class);
			if(!iniciarAplicacao(mdi, perfil))
				abrirCadastroPerfil(frm, new Usuario());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
