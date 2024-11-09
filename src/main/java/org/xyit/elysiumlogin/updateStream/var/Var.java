package org.xyit.elysiumlogin.updateStream.var;

import java.util.ArrayList;

public class Var {
    private ArrayList<Object> var;

    public Var(ArrayList<Object> var) {
        this.var = var;
    }

    public Var() {
    }

    public ArrayList<Object> getVar() {
        return var;
    }

    public void setVar(ArrayList<Object> var) {
        this.var = var;
    }

    public void addVar(Object var){
        if (!this.var.contains(var) && var != null) {
            this.var.add(var);
        }
    }

    public void removeVar(Object var){
        if (var == null){
            return;
        }
        while (this.var.contains(var)) {
            this.var.remove(var);
        }
    }
}
