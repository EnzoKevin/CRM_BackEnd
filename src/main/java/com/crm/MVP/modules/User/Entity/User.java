package com.crm.MVP.modules.User.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class User {
    private Long id;
    private String nome;
    private String email;
    private boolean admin;
    private String idioma;
    private String fusoHorario;
    private String formatoDeData;
    private boolean notificacoesEmail;
    private boolean alertasWeb;
    private boolean resumoSemanal;

    // Getters and Setters
    
public Long getId() {
        return id;
    }
public void setId(Long id) {
        this.id = id;
    }

public String getNome() {
        return nome;
    }
public void setNome(String nome) {
        this.nome = nome;
    }

public String getEmail() {
        return email;
    }
public void setEmail(String email) {
        this.email = email;
    }

public boolean isAdmin() {
        return admin;
    }

public void setAdmin(boolean admin) {
        this.admin = admin;
    }

public String getIdioma() {
        return idioma;
    }
public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

public String getFusoHorario() {
        return fusoHorario;
    }

public void setFusoHorario(String fusoHorario) {
        this.fusoHorario = fusoHorario;
    }

public String getFormatoDeData() {
        return formatoDeData;
    }
public void setFormatoDeData(String formatoDeData) {
        this.formatoDeData = formatoDeData;
    }

public boolean isNotificacoesEmail() {
        return notificacoesEmail;
    }

public void setNotificacoesEmail(boolean notificacoesEmail) {
        this.notificacoesEmail = notificacoesEmail;
    }

public boolean isAlertasWeb() {
        return alertasWeb;
    }
public void setAlertasWeb(boolean alertasWeb) {
        this.alertasWeb = alertasWeb;
    }

public boolean isResumoSemanal() {
        return resumoSemanal;
    }
public void setResumoSemanal(boolean resumoSemanal) {
        this.resumoSemanal = resumoSemanal;
    }
public static Object builder() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'builder'");
}



}
