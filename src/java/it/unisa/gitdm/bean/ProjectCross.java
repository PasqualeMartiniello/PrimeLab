/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.gitdm.bean;


import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author pasqualemartiniello
 */
public class ProjectCross implements Serializable{
    
    
    private String name;
    private String gitUrl;
    
    public ProjectCross(String name, String gitUrl){
        this.name = name;
        this.gitUrl = gitUrl;
    }
    
    public String getName(){
        return name;
    }
    
    public String getGitUrl(){
        return gitUrl;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setGitUrl(String gitUrl){
        this.gitUrl = gitUrl;
    }
    
    @Override
    public boolean equals(Object anotherObject){
        if(anotherObject == null) return false;
        if(getClass() != anotherObject.getClass()) return false;
        ProjectCross object = (ProjectCross) anotherObject;
        return (getName().equals(object.getName()) && getGitUrl().equals(object.getGitUrl()));
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.name);
        hash = 41 * hash + Objects.hashCode(this.gitUrl);
        return hash;
    }
  
    public String toString(){
        return getClass().getName() + "{name= " + getName() + ", gitUrl=" + getGitUrl() +" }";
    }
}
