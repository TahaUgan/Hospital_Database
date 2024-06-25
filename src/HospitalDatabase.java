

public class HospitalDatabase {

    private PatientNode root;

    private class PatientNode {
        String patientName;
        String docName;
        int visitDay, visitMonth, visitYear;
        CareTeamMemberNode medicalTeamHead; 
        PatientNode left, right;

        PatientNode(String Name, String docName, int day, int month, int year) {

            this.patientName = Name;
            this.docName = docName;
            this.visitDay = day;
            this.visitMonth = month;
            this.visitYear = year;

            this.medicalTeamHead = null;
            this.left = null;
            this.right = null;
        }

      
    }

    private class CareTeamMemberNode {
        String memberName;
        String memberRole;
        CareTeamMemberNode left, right;

        CareTeamMemberNode(String memberName, String memberRole) {
            this.memberName = memberName;
            this.memberRole = memberRole;
            this.left = null;
            this.right = null;
        }
    }



    /**
     * Creates a hospital database
     */
    public HospitalDatabase() {
        this.root = null;
    }




    /**Creates and inserts new patient into hospital database with given parameters as patient information
     * <p>Calls inner/private method <href>{@link #addPatient(PatientNode, String, String, int, int, int)}</href>to insert a patient to hospital Database 
     * 
     * @param patientName New patients name
     * @param doctorName New patient's doctor name
     * @param visitDay New patients visit Day to Hospital
     * @param visitMonth New patients visit month to Hospital
     * @param visitYear New patients visit year to Hospital
     */
    public void addPatient(String patientName, String doctorName, int visitDay, int visitMonth, int visitYear) {
        
        if(patientName == null){ // to handle null name input
            
            return;
        }else{
        root = addPatient(root, patientName, doctorName, visitDay, visitMonth, visitYear);
        }
    }
    
    /**
     * If root is empty it inserts new patient into root. Otherwise, travels through the tree to
     *  find appropriate place to insert New patient and inserts new patient.
     * Traveling is made recursive, look through every patientNode and if new patient's <code>patientName</code>
     *  has smaller value than selected node's goes left to look for place and otherwise goes right node to look.
     *  If selected node is empty places new patient there  
     * 
     * @param x Used fo recursion, selectedNode in every recursive call
     * @param patientName New patients name
     * @param doctorName New patient's doctor name
     * @param visitDay New patients visit Day to Hospital
     * @param visitMonth New patients visit month to Hospital
     * @param visitYear New patients visit year to Hospital
     * @return
     */
    private PatientNode addPatient(PatientNode x, String patientName, String doctorName, int visitDay, int visitMonth, int visitYear) {
        

        if (x == null) {

            System.out.println("INFO: Patient " + patientName + " has been added");
            
            return new PatientNode(patientName, doctorName, visitDay, visitMonth, visitYear);
        }

        int cmp = patientName.compareTo(x.patientName);
    
        if(cmp < 0){// if smaller check left..

            x.left = addPatient(x.left, patientName, doctorName, visitDay, visitMonth, visitYear);
            
        }else if(cmp > 0){ //if bigger check right..
            
            x.right = addPatient(x.right, patientName, doctorName, visitDay, visitMonth, visitYear);

        }else{ // and if it is same, update the information

            x.patientName = patientName;
            x.docName = doctorName;
            x.visitDay = visitDay;
            x.visitMonth = visitMonth;
            x.visitYear = visitYear;

            System.out.println("ERROR: Patient " + patientName + " overwritten");

        }



        return x;
    }
    



    /**Removes patient with given name from the database tree
     * 
     * <p>Calls inner/private method <href>{@link #removePatient(PatientNode, String)}</href> starting from root.
     * @param patientName patient name to be searched and deleted
     */
    public void removePatient(String patientName){

        System.out.println(); // empty line, same reason as others

        root = removePatient(root, patientName); // starting with root

        System.out.println("INFO: Patient " + patientName + " has been removed"); 
        
    }
    
    /**Removes patient with given name from hospital database tree. Travels through tree according to patientName 
     * e.g left if given Name is smaller than selected nodes name, and right if otherwise.
     * When it finds the wanted node, it looks how many child nodes it has. If it has no child nodes then removes directly.
     *  If it has only one child, then removes this node and places it's child to removedNodes place.
     * If it has 2 childs, removes this and selects a successor node to take removedNode's place.
     * Successor is selected as smallest Node from right side of removedNode.
     * 
     * @param x Used for recursion, to travel and select wanted node
     * @param patientName patient name to be saerched
     * @return patient removed from database 
     */
    private PatientNode removePatient(PatientNode x, String patientName) {

        if (x == null) {
            
            System.out.println("ERROR: Patient" + patientName + " does not exist");
            return null;

        }
    
        if (patientName.equals(x.patientName)) {

            if (x.left == null && x.right == null) { // case when has no child

                return null;

            } else if (x.left == null) { // no child is false and if left is null as well, then only child is the one in the right

                return x.right;

            } else if (x.right == null) { // same thing with previous else if, but reverse situation

                return x.left;

            } else { // when node has 2 childs

                PatientNode smaller = findSmallestNode(x.right); // selecting successor from right side

                // transfering successor info to removed node
                x.patientName = smaller.patientName;
                x.docName = smaller.docName;
                x.visitDay = smaller.visitDay;
                x.visitMonth = smaller.visitMonth;
                x.visitYear = smaller.visitYear;

                // System.out.println("INFO: Patient " + patientName + " has been removed"); // TO-DO: not working check again
                
                x.right = removePatient(x.right, smaller.patientName);// erasing remains at the old place of successor node
                
                return x;
            }
        } 
        //if we cant find, than we use comparsion using key attribute which makes patient name, we progress until finding using recusrion
        if (patientName.compareTo(x.patientName) < 0){

            x.left = removePatient(x.left, patientName);
            return x;

        }
    
        x.right = removePatient(x.right, patientName);
        return x;
    }
    






    
    /** Starts from root and finds smallet node by going left until it is empty, made for pateints
     * @param root , first patient in the database tree
     * @return returns the patient with the smallest value according the name e.g 'A' < 'B' thus returns 'A'
     */
    private PatientNode findSmallestNode(PatientNode root){
        
        while (root.left != null) {
            root = root.left;
        }

        return root;

    }

    
    /** Starts from root in the sub-tree of a patient which holds medical members and finds smallet node by going left until it is empty, made for medic team memmbers
     * @param node is the first medical staff in a sub-tree of some patient
     * @return returns the medical staff with the smallest value according the name e.g 'A' < 'B' thus returns 'A'
     */
    private CareTeamMemberNode findSmallestNode(CareTeamMemberNode node){
        
        while (node.left != null) {
            node = node.left;
        }

        return node;
    }
    






    /**Searches for patient with name as given <code>{@link patientName}</code> and adds new member to that patient.
     * 
     * <p>Calls inner/private method <href>{@link #addMember(MedicalTeamNode, String, String)}</href> starting from root member of given patient.
     * 
     * @param patientName wanted patient name
     * @param memberName New member's Name
     * @param memberRole New member's Role
     */
    public void addMember(String patientName, String memberName, String memberRole){

        PatientNode patient = findPatient(root, patientName);

        if (patient == null) {
           
            System.out.println("ERROR: Patient " + patientName + " does not exist");
            return;
           
        }

        patient.medicalTeamHead = addMember(patient.medicalTeamHead, memberName, memberRole);
        System.out.println("INFO: " + memberName + " has been added to the patient " +  patientName);
    }
    
    /**
     * Starts from root member of the patient and looks for appropriate place for new member using recursion and memberName as Key.
     * 
     * 
     * @param node to use recursion, selected memberNode to look
     * @param memberName New member's name
     * @param memberRole New member's Role
     * @return selected null node
     */
    private CareTeamMemberNode addMember(CareTeamMemberNode node, String memberName, String memberRole){

        if (node == null) {

            return new CareTeamMemberNode(memberName, memberRole);
        }
        
        int cmp = memberName.compareTo(node.memberName);

        if (cmp < 0){

            node.left = addMember(node.left, memberName, memberRole);
            
        }else if(cmp > 0){

            node.right = addMember(node.right, memberName, memberRole);
            
        }else{// same memberName, when cmp == 0

            node.memberName = memberName;
            node.memberRole =memberRole;

            System.out.println("ERROR: Member " + memberName + " overwritten");


        }
    
        return node;
    }
    








    /**Removes member with given <code>memberName</code> from the patient with given <code>patientName</code>
     * Searches through database to find wanted patient using <href>{@link #findPatient(PatientNode, String)}</href> method.
     * <p>Calls inner/private method <href>{@link #removeMember(MedicalTeamNode, String)}</href> starting from root member of given patient.
     * 
     * @param patientName wanted patient name
     * @param memberName name of member to be removed
     */
    public void removeMember(String patientName, String memberName){
        
        PatientNode patient = findPatient(root, patientName);
        
        
        if (patient == null) {

            System.out.println("ERROR: Patient " + patientName + " not found");
            return;
        
        }

        patient.medicalTeamHead = removeMember(patient.medicalTeamHead, memberName);
    }
    
    /**Removes member with given name from selected patient.
     * it looks how many child nodes it has. If it has no child nodes then removes directly.
     *  If it has only one child, then removes this node and places it's child to removedNodes place.
     * If it has 2 childs, removes this and selects a successor node to take removedNode's place.
     * Successor is selected as smallest Node from right side of removedNode.
     * 
     * @param x
     * @param memberName
     * @return
     */
    private CareTeamMemberNode removeMember(CareTeamMemberNode x, String memberName){

        if (x == null) {

            System.out.println("ERROR: Member not found");
            return null;

        }
    
        if (memberName.equals(x.memberName)) {

            if (x.left == null && x.right == null) {
            
                return null;
            
            } else if (x.left == null) {
            
                return x.right;
            
            } else if (x.right == null) {
            
                return x.left;
            
            } else {
                //selects a successor member from the right side of the node to be removed
            
                CareTeamMemberNode smallerOne = findSmallestNode(x.right);
            
                x.memberName = smallerOne.memberName;
                x.memberRole = smallerOne.memberRole;

                x.right = removeMember(x.right, smallerOne.memberName);
                return x;

            }
        } 
    
        if (memberName.compareTo(x.memberName) < 0){

            x.left = removeMember(x.left, memberName);
            return x;

        }
    
        x.right = removeMember(x.right, memberName);
        return x;
    }
    





    /**Prints out all patients <code>patientName</code>, <code>visitDate</code> and <code>docName</code> in the database tree <p>
     * Calls <href>{@link #travelAll(PatientNode)}</href> method with root patientNode as starting point
     * 
     */
    public void showAllPatients(){
        if (root == null) {

            System.out.println("---none---");
            System.out.println(); // empty line after ---none--- text to seperate infos
            
        } else {

            System.out.println(); // empty line before lsiting patients
            travelAll(root);
            
        }
    }
    
    /**Starts from <code>node</code> and goes every patientNode. Prints all patients information in ascending order.
     * @param node , To use recursion, starting node
     */
    private void travelAll(PatientNode node){

        if (node != null) {
            travelAll(node.left);

            System.out.println(node.patientName + ", " + node.visitYear + ", " + node.docName);
            
            travelAll(node.right);
        }
    }
    


    /**searches and return patientNode with patientName as <code>ptntName</code>. Uses recursion and <code>compareTo(String)</code> method
     * to search patientNode. if <code>roots patientName</code> has smaller value than <code>ptntName</code> goes to leftNode and goes to rightNode if otherwise. 
     * 
     * @see java.lang.String#compareTo(String) compareTo(String)
     * 
     * @param root to use recursion, root because search starts from root Node
     * @param ptntName , Patient Name to be searched
     * @return PatientNode with patientName as <code>ptntName</code>
     */
    private PatientNode findPatient(PatientNode root, String ptntName){

        if(ptntName == null){
            return null;
        }

        if (root == null || root.patientName.equals(ptntName)) {
            return root;
        }

        if (ptntName.compareTo(root.patientName) < 0){

            return findPatient(root.left, ptntName);
            
        } else {

            return findPatient(root.right, ptntName);
            
        }
    }


    /**Prints all <code>patient Name</code> and <code>visit day/month</code> for patients who have visited hospital given <code>year</code>
     * <p> Calls the private/inner method <href>{@link #travelToFindYearMatchings(PatientNode, int)}</href>. Starts recursion from root patientNode.
     *
     *  @param year filtering year to search patients
     */
    public void showPatients(int year){

        System.out.println();
        System.out.println(year);

        travelToFindYearMatchings(root, year);

    }

    /**Travels all patientNodes to find and print <code>patientName</code> and 
     * <code>visit day/month</code> for patients who have their <code>visitYear</code> as <code>year</code>
     * 
     * @param node Used to travel every node using recursion
     * @param year ,Filtering year to search
     */
    private void travelToFindYearMatchings(PatientNode node, int year){

        //first node will be root

        if (node != null) {
            
            if(node.visitYear == year){
                                
                System.out.println(node.patientName + ", " + node.visitDay + "/" + node.visitMonth);
                            
            }

            travelToFindYearMatchings(node.left, year);
            
            travelToFindYearMatchings(node.right, year);
        }

        

    }



    
    /** This method prints <code>patient Name</code> and <code>visit date</code> for all patients who have their doctor as given <code>doctorName</code> <p>
     * Calls the private method <href>{@link #showDoctorPatients(PatientNode, String)}</href> starting recursion from root Patient of the database tree.
     * @param doctorName Name to be searched through the tree
     */
    public void showDoctorPatients(String doctorName){

        System.out.println();// empty line berofe information to match testOutput

        System.out.println(doctorName);
        showDoctorPatients(root, doctorName);

    }
    


    /**Prints given <code>doctorName</code> and all patients <code>Name</code> and <code>visit date</code> 
     * that have Doctor as <code>doctorName</code>. As this tree uses <code>patientName</code> as Key, we need to travel
     * every patient to see if they have <code>docName</code> as <code>doctorName</code>. 
     * 
     * @param node , Used for traveling all nodes using recursion.
     * @param doctorName docName to be searched in every patient node
     */
    private void showDoctorPatients(PatientNode node, String doctorName) {

        if (node == null) {
            return; // to stop recursiving of this method when it finds a branch empty stops search in that branch
        }
    
        showDoctorPatients(node.left, doctorName);
    
        if (node.docName.equals(doctorName)) {

            String date = (node.visitDay + "/" + node.visitMonth + "/" + node.visitYear);
            System.out.println(node.patientName + ", " + date);
            
        }
    
        showDoctorPatients(node.right, doctorName);
    }
    





    /**Prints information about given patient. First searches the patient name in the tree using 
     * <href>{@link #findPatient(PatientNode, String)}</href> method. After that
     * prints Error message if patient does not exist in Database tree, but if patient exists it prints patients
     * name, visit date and doctor name. Lastly if there exists and staff member other than doctor 
     * it prints them using <href>{@link #listMedicalTeam(MedicalTeamNode)}</href> method
     * 
     * @param patientName , Name of the patient to be searced in database tree
     */
    public void showPatient(String patientName) {

        PatientNode patient = findPatient(root, patientName);

        System.err.println(); // empty line before info

        if (patient == null) {

            System.out.println("ERROR: Patient not found");
            return;

        }
    
        // patient infos are being printed 
        System.out.println(patient.patientName);                                                    //Name
        System.out.println(patient.visitDay + "/" + patient.visitMonth + "/" + patient.visitYear);  //date
        System.out.println(patient.docName);                                                    //docName
    
        //showing staff membres here
        if (patient.medicalTeamHead != null) {
    
            listMedicalTeam(patient.medicalTeamHead);
            
        }
        
    }
    


    /** Prints medical team members in the tree starting from root using recursion.
     * Recursion works followingly: <p>
     * Recursion goes on until it finds a null node in tree and start to print LeftNode, itself, RightNode respectively.
     * This process goes on starting lowermost to uppermost medicalTeamNode.
     * @param root , first member in medical staff
     */
    public void listMedicalTeam(CareTeamMemberNode root){

        if (root != null) {
            
            listMedicalTeam(root.left);
            System.out.println(root.memberName + ", " + root.memberRole);
            listMedicalTeam(root.right);
            
        }

    }
    
  
    






}
