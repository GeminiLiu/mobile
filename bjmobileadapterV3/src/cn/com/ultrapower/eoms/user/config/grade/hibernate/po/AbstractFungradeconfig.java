package cn.com.ultrapower.eoms.user.config.grade.hibernate.po;



/**
 * AbstractFungradeconfig generated by MyEclipse - Hibernate Tools
 */

public abstract class AbstractFungradeconfig  implements java.io.Serializable {


    // Fields    

     private Long fungradeconfigId;
     private String fungradeconfigGradnode;
     private Long fungradeconfigGradvalue;


    // Constructors

    /** default constructor */
    public AbstractFungradeconfig() {
    }

	/** minimal constructor */
    public AbstractFungradeconfig(Long fungradeconfigId) {
        this.fungradeconfigId = fungradeconfigId;
    }
    
    /** full constructor */
    public AbstractFungradeconfig(Long fungradeconfigId, String fungradeconfigGradnode, Long fungradeconfigGradvalue) {
        this.fungradeconfigId = fungradeconfigId;
        this.fungradeconfigGradnode = fungradeconfigGradnode;
        this.fungradeconfigGradvalue = fungradeconfigGradvalue;
    }

   
    // Property accessors

    public Long getFungradeconfigId() {
        return this.fungradeconfigId;
    }
    
    public void setFungradeconfigId(Long fungradeconfigId) {
        this.fungradeconfigId = fungradeconfigId;
    }

    public String getFungradeconfigGradnode() {
        return this.fungradeconfigGradnode;
    }
    
    public void setFungradeconfigGradnode(String fungradeconfigGradnode) {
        this.fungradeconfigGradnode = fungradeconfigGradnode;
    }

    public Long getFungradeconfigGradvalue() {
        return this.fungradeconfigGradvalue;
    }
    
    public void setFungradeconfigGradvalue(Long fungradeconfigGradvalue) {
        this.fungradeconfigGradvalue = fungradeconfigGradvalue;
    }
   








}