
import static java.lang.Thread.sleep;
import java.util.concurrent.Semaphore;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Joseph
 */
public class BarberShop extends javax.swing.JFrame {

    private int numSeats;
    private int seatsAvailable;
    private int numCustomers;
    private int customersInShop;
    private Barber barber = new Barber();
    private Customer customer = new Customer();
    private Semaphore barberSem = new Semaphore(0);
    private Semaphore customerSem = new Semaphore(0);
    private Semaphore seatsSem = new Semaphore(1);
    
    public BarberShop() {
        initComponents();
    }
    
    public class Barber extends Thread {
    
    private String name;
    
    public Barber(){
        name = "bob";
    }
    public Barber(String n){
        name = n;
    }
    
    public void enterBarberShop(){
        textArea.append("The Barber: " + name + " Arrives!");
        work();
    }
    
    
    public void work(){
        while(numCustomers > 0){
            
        if(numSeats == seatsAvailable)
                    barberStatus.setText("Barber Status: sleep");
        
            try{
                customerSem.acquire();
                
                seatsSem.release();
                barberSem.release();
                seatsSem.release();
                barberStatus.setText("Barber Status: cutting..");
                seatsAvailable++;
                sleep(1000);
                
                seatsAvailableLabel.setText("Seats Available: " + seatsAvailable);

                

            }
            catch (InterruptedException e){
                    
            }
        }
    }
    
    public void run(){
        enterBarberShop();
    }
}

    
    public class Customer extends Thread {
    private int customerNum;
    private String customerName;
    private boolean customerStatus = true;
    
    public Customer(){
        customerNum = 0;
    }
    public Customer(int num){
        customerNum = num;
    }
    
    
    public void leave(){
        textArea.append("\nCustomer: " + customerNum + " is done.");
        // when customer leaves decrement
        customersInShop--;
        // customer is done, and leaves
        customerStatus = false;
        customersPresentLabel.setText("Customers present: " + customersInShop);
    }
    
    public void enterBarberShop(){
        if(seatsAvailable > 0){
            sitDown();
        }
        else{
            seatsSem.release();
            textArea.append("\nNo seat for: "+ customerNum);
            leave();
        }
    }
    
    public void sitDown(){
        customerSem.release();
        seatsSem.release();
        seatsAvailable--;
        seatsAvailableLabel.setText("Seats Available: " + seatsAvailable);
        try{
            barberSem.acquire();
            sleep(1000);
            leave();
        }
        catch (InterruptedException e){

        }
    }
    
    public void run(){
        while(customerStatus == true){
            try{
                seatsSem.acquire();
                enterBarberShop();
            }
            catch (InterruptedException e){

            }
        }
    }
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        numCustomersField = new javax.swing.JTextField();
        openShopButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        numCustomersLabel = new javax.swing.JLabel();
        numSeatsField = new javax.swing.JTextField();
        numSeatsLabel = new javax.swing.JLabel();
        barberStatus = new javax.swing.JLabel();
        seatsAvailableLabel = new javax.swing.JLabel();
        customersPresentLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        numCustomersField.setText("10");
        numCustomersField.setToolTipText("");

        openShopButton.setText("Open Shop!");
        openShopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openShopButtonActionPerformed(evt);
            }
        });

        textArea.setColumns(20);
        textArea.setRows(5);
        jScrollPane1.setViewportView(textArea);

        numCustomersLabel.setText("Enter number of customers:");

        numSeatsField.setText("5");

        numSeatsLabel.setText("Enter Number of Seats");

        barberStatus.setText("Barber Status: ");

        seatsAvailableLabel.setText("Number of Seats Available:");

        customersPresentLabel.setText("Customers Present: ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 547, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(openShopButton)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(numCustomersLabel)
                        .addGap(18, 18, 18)
                        .addComponent(numCustomersField, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(numSeatsLabel)
                        .addGap(18, 18, 18)
                        .addComponent(numSeatsField, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(barberStatus)
                    .addComponent(seatsAvailableLabel)
                    .addComponent(customersPresentLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(barberStatus)
                .addGap(18, 18, 18)
                .addComponent(seatsAvailableLabel)
                .addGap(18, 18, 18)
                .addComponent(customersPresentLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numCustomersField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(numCustomersLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numSeatsField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(numSeatsLabel))
                .addGap(18, 18, 18)
                .addComponent(openShopButton)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void openShopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openShopButtonActionPerformed
        // TODO add your handling code here:
        numCustomers = Integer.parseInt(numCustomersField.getText());
        customersInShop = numCustomers;
        numSeats = Integer.parseInt(numSeatsField.getText());
        seatsAvailable = numSeats;
        openBarberShop();
    }//GEN-LAST:event_openShopButtonActionPerformed

    
    public void openBarberShop(){
        // start new thread for barber
        Barber b = new Barber();
        b.start();
        int i = 0;
        
        // create thread for each customer
        while(i < numCustomers){
            
            Customer customer = new Customer(i);
            customer.start();
            //customersPresentLabel.setText("Customers present: " + numCustomers);
            i++;
            try {
                sleep(100);
              } catch(InterruptedException ex) {};
        }
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BarberShop.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BarberShop.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BarberShop.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BarberShop.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BarberShop().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel barberStatus;
    private javax.swing.JLabel customersPresentLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField numCustomersField;
    private javax.swing.JLabel numCustomersLabel;
    private javax.swing.JTextField numSeatsField;
    private javax.swing.JLabel numSeatsLabel;
    private javax.swing.JButton openShopButton;
    private javax.swing.JLabel seatsAvailableLabel;
    private javax.swing.JTextArea textArea;
    // End of variables declaration//GEN-END:variables
}
