package fr.usmb.m1isc.compilation.tp;

public class Arbre {
    // attributs ----------------------------------
    private String racine;
    private Arbre membreDroit;
    private Arbre membreGauche;

    // constructeurs ------------------------------
    Arbre(){
        this.membreDroit = null;
        this.membreGauche = null;
        this.racine = null;
    }

    Arbre(String racine){
        this.membreDroit = null;
        this.membreGauche = null;
        this.racine = racine;
    }

    Arbre(Arbre membreDroit){
        this.membreDroit = membreDroit;
        this.membreGauche = null;
        this.racine = null;
    }

    Arbre(String racine, Arbre membreDroit){
        this.membreDroit = membreDroit;
        this.membreGauche = null;
        this.racine = racine;
    }

    Arbre(String racine, Arbre membreGauche, Arbre membreDroit){
        this.membreDroit = membreDroit;
        this.membreGauche = membreGauche;
        this.racine = racine;
    }

    // getter/setter ----------------------------
    public Arbre getMembreDroit() {
        return membreDroit;
    }

    public Arbre getMembreGauche() {
        return membreGauche;
    }

    public String getRacine() {
        return racine;
    }

    public void setMembreDroit(Arbre filsDroit) {
        this.membreDroit = filsDroit;
    }

    public void setMembreGauche(Arbre filsGauche) {
        this.membreGauche = filsGauche;
    }

    public void setRacine(String racine) {
        this.racine = racine;
    }

    public String afficherArbre(){
        if(this.membreDroit == null && this.membreGauche == null){
            return this.racine;
        }
        String temp = "(";
        temp += this.racine;

        if(this.membreGauche != null){
            temp += " " + this.membreGauche.afficherArbre();
        }

        if(this.membreDroit != null){
            temp += " " + this.membreDroit.afficherArbre();
        }

        temp += ")";

        return temp;
    }

    public String generer(){
        if(this.getRacine().equals("INPUT")){
            return "\tin eax\n";
        }
        if(this.getRacine().equals(">")){
            return "\tmov eax, " + String.valueOf(Integer.valueOf(this.membreGauche.getRacine()) - Integer.valueOf(this.membreGauche.getRacine())) + "\n";
        }
        if(this.getRacine().equals(">=")){
            return "\tmov eax, " + String.valueOf(Integer.valueOf(this.membreGauche.getRacine()) - Integer.valueOf(this.membreGauche.getRacine()) + 1) + "\n";
        }
        return "\tmov eax, " + this.getRacine() + "\n";
    }

    public String code_segment(){
        if(this.getRacine().equals("LET")){
            return this.membreDroit.code_segment() + "\tpop eax\n\tmov " + this.membreGauche.getRacine() + ", eax\n\tpush eax\n";
        }
        else if(this.getRacine().equals(";")){
            return this.membreGauche.code_segment() + this.membreDroit.code_segment();
        }
        else if(this.getRacine().equals(">")){
            return this.code_segment() + "\tpop eax\n" +
                    "\tjl vrai\n" +
                    "\tpush 0\n" +
                    "\tjmp fin\n" +
                    "\tvrai : push 1\n" +
                    "\tfin :\n";
        }
        else if(this.getRacine().equals(">=")){
            return this.code_segment() + "\tpop eax\n" +
                    "\tjl vrai\n" +
                    "\tpush 0\n" +
                    "\tjmp fin\n" +
                    "\tvrai : push 1\n" +
                    "\tfin :\n";
        }
        else if(this.getRacine().equals("+")){
            return this.membreGauche.code_segment() +
                    this.membreDroit.code_segment() +
                    "\tpop ebx\n" +
                    "\tpop eax\n" +
                    "\tadd eax, ebx\n" +
                    "\tpush eax\n";
        }
        else if(this.getRacine().equals("*")){
            return this.membreGauche.code_segment() +
                    this.membreDroit.code_segment() +
                    "\tpop ebx\n" +
                    "\tpop eax\n" +
                    "\tmul eax, ebx\n" +
                    "\tpush eax\n";
        }
        else if(this.getRacine().equals("/")){
            return this.membreGauche.code_segment() +
                    this.membreDroit.code_segment() +
                    "\tpop ebx\n" +
                    "\tpop eax\n" +
                    "\tdiv eax, ebx\n" +
                    "\tpush eax\n";
        }
        else if(this.getRacine().equals("MOD")){
            return this.membreGauche.code_segment() +
                    this.membreDroit.code_segment() +
                    "\tpop ebx\n" +
                    "\tpop eax\n" +
                    "\tdiv eax, ebx\n" +
                    "\tpush eax\n";
        }
        else if(this.getRacine().equals("WHILE")){
            String temp = "";
            temp += "debut_while_1:\n";
            temp += this.membreGauche.code_segment();
            temp += "faux_gt_1:\n\tmov eax,0\n";
            temp += "sortie_gt_1:\n\tsortie_while_1\n";
            temp += this.membreDroit.code_segment();
            temp += "\tjmp debut_while_1\n";
            return temp;
        }
        else/*(this.getRacine().chars().allMatch(Character::isDigit))*/{
            return this.generer();
        }/*
        else{
            String tmp = "";
            tmp += this.membreGauche.code_segment();
            tmp += this.membreDroit.code_segment();
            return tmp;
        }*/
    }

    public String data_segment(){
        String tmp = "";
        if(this.racine.equals("LET")){
            tmp += "\t" + this.membreGauche.getRacine() + " DD\n";
        }
        else{
            if(this.membreGauche != null){
                tmp += this.membreGauche.data_segment();
            }
            if(this.membreDroit != null){
                tmp += this.membreDroit.data_segment();
            }
        }
        return tmp;
    }

}