package cn.com.ultrapower.eoms.user.config.grade.hibernate.po;



/**
 * AbstractDepgradeconfig generated by MyEclipse - Hibernate Tools
 */

public abstract class AbstractDepgradeconfig  implements java.io.Serializable {


    // Fields    

     private Long depgradeconfigId;
     private String depgradeconfigDep;
     private Double depgradeconfigGradvalue;
     private Long depgradeconfigAffectbusiness;


    // Constructors

    /** default constructor */
    public AbstractDepgradeconfig() {
    }

    
    /** full constructor */
    public AbstractDepgradeconfig(String depgradeconfigDep, Double depgradeconfigGradvalue, Long depgradeconfigAffectbusiness) {
        this.depgradeconfigDep = depgradeconfigDep;
        this.depgradeconfigGradvalue = depgradeconfigGradvalue;
        this.depgradeconfigAffectbusiness = depgradeconfigAffectbusiness;
    }

   
    // Property accessors

    public Long getDepgradeconfigId() {
        return this.depgradeconfigId;
    }
    
    public void setDepgradeconfigId(Long depgradeconfigId) {
        this.depgradeconfigId = depgradeconfigId;
    }

    public String getDepgradeconfigDep() {
        return this.depgradeconfigDep;
    }
    
    public void setDepgradeconfigDep(String depgradeconfigDep) {
        this.depgradeconfigDep = depgradeconfigDep;
    }

    public Double getDepgradeconfigGradvalue() {
        return this.depgradeconfigGradvalue;
    }
    
    public void setDepgradeconfigGradvalue(Double depgradeconfigGradvalue) {
        this.depgradeconfigGradvalue = depgradeconfigGradvalue;
    }

    public Long getDepgradeconfigAffectbusiness() {
        return this.depgradeconfigAffectbusiness;
    }
    
    public void setDepgradeconfigAffectbusiness(Long depgradeconfigAffectbusiness) {
        this.depgradeconfigAffectbusiness = depgradeconfigAffectbusiness;
    }
   








}