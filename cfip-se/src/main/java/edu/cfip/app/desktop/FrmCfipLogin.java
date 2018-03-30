package edu.cfip.app.desktop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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
				logarImpl();
			}
		});
	}

	private void logarImpl() {
		try {
			Usuario usuario = repositorio.login(getLogin(), getSenhaMD5());
			setPerfil(usuario);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
