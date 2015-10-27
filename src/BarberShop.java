/*
Joseph Andaya
COMP 173
Lab 6 
*/


import java.awt.Color;
import static java.lang.Thread.sleep;
import java.util.concurrent.Semaphore;

public class BarberShop extends javax.swing.JFrame {

    private int numSeats;
    private int seatsAvailable;
    private int numCustomers;
    private int customersInShop;
    private int sleepSeconds;
    private Barber barber = new Barber();
    private Customer customer = new Customer();
    private Semaphore barberSeatSem = new Semaphore(0);
    private Semaphore waitingSeatSem = new Semaphore(0);
    
    public BarberShop() {
        initComponents();
    }
    
    public class Barber extends Thread {
    
    private String name;
    private int barberNum;
    
    public Barber(){
        name = "Bob";
    }
    public Barber(String n){
        name = n;
    }
    // initialize barber
    public void enterBarberShop(){
        textArea.append("The Barber: " + name + " Arrives!");
        work();
    }
    
    public void finishCustomer(){
        // frees up the barber's seat
        barberSeatSem.release(1);
    }
    
    public void work(){
        while(numCustomers > 0){
            
        // if all seats are available that means there are no customers, and the barber sleeps.    
        if(numSeats == seatsAvailable)
            barberStatus.setText("Barber Status: sleep");
        
            try{
                // get a customer
                waitingSeatSem.acquire(1);

                // perform work on customer
                barberStatus.setText("Barber Status: cutting..");
                finishCustomer();
                suspendSleep();
                
                // once finished free up a seat
                seatsAvailable++;
                seatsAvailableLabel.setText("Seats Available: " + seatsAvailable);
            }
            catch (InterruptedException e){
                    System.err.printf("Error on lock");
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
    private int customerStatus = 0;
    
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
        
        // update number of customers present on GUI
        customersPresentLabel.setText("Customers present: " + customersInShop);
        
        // if there are no customers in shop barber sleeps
        if(customersInShop <= 0)
                    barberStatus.setText("Barber Status: sleep");
    }
    
    public void enterBarberShop(){
        // customers will sit down if they find a seat, if not they leave
        if(seatsAvailable > 0){
            sitDown();
        }
        else{
            //waitingChairsSem.release(1);
            textArea.append("\nNo seat for: "+ customerNum);
            leave();
        }
    }
    
    public void tryBarber(){
        try{
            //Try to sit in barber's seat
            barberSeatSem.acquire(1);
            // gets haircut
            suspendSleep();
            // leaves shop, frees available seat
            leave();
        }
        catch (InterruptedException e){
            System.err.printf("Error on lock");
        }
    }
    
    public void sitDown(){
        // increments the amount of customers coming in on semaphore
        waitingSeatSem.release(1);
        // decrement number of seats available in waiting room
        seatsAvailable--;
        seatsAvailableLabel.setText("Seats Available: " + seatsAvailable);
        tryBarber();
    }
    
    public void run(){
        // when a customer needs a haircut
            enterBarberShop();
    }
}
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
        sleepLabel = new javax.swing.JLabel();
        sleepField = new javax.swing.JTextField();
        errorLabel = new javax.swing.JLabel();

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

        sleepLabel.setText("Enter barber efficiency (seconds): ");

        sleepField.setText("1");

        errorLabel.setText("Positive numbers only!");

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
                        .addComponent(numSeatsLabel)
                        .addGap(18, 18, 18)
                        .addComponent(numSeatsField, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(numCustomersLabel)
                            .addComponent(sleepLabel)
                            .addComponent(errorLabel))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(numCustomersField, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                            .addComponent(sleepField))))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(errorLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sleepLabel)
                    .addComponent(sleepField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
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
        // initializes all values based on GUI Field
        numCustomers = Integer.parseInt(numCustomersField.getText());
        customersInShop = numCustomers;
        numSeats = Integer.parseInt(numSeatsField.getText());
        seatsAvailable = numSeats;
        sleepSeconds = Integer.parseInt(sleepField.getText());
        // input validation ensure they're positive and not 0
        if (sleepSeconds <= 0 || numSeats <= 0 || numCustomers <= 0)
            errorLabel.setForeground(Color.red);
        else
        {
            errorLabel.setForeground(Color.black);
            openBarberShop();
        }
    }//GEN-LAST:event_openShopButtonActionPerformed

    // uses input from user for how long to sleep
    public void suspendSleep(){
        try {
                sleep(1000 * sleepSeconds);
            } catch(InterruptedException ex) {
                    System.err.printf("Sleep Error.");
            };
    }
    public void openBarberShop(){
        int i = 0;
        // start new thread for barber
        barber.start();
        
        // create thread for each customer and start action
        while(i < numCustomers){
            Customer customer = new Customer(i);
            customer.start();
            i++;
        }
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BarberShop().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel barberStatus;
    private javax.swing.JLabel customersPresentLabel;
    private javax.swing.JLabel errorLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField numCustomersField;
    private javax.swing.JLabel numCustomersLabel;
    private javax.swing.JTextField numSeatsField;
    private javax.swing.JLabel numSeatsLabel;
    private javax.swing.JButton openShopButton;
    private javax.swing.JLabel seatsAvailableLabel;
    private javax.swing.JTextField sleepField;
    private javax.swing.JLabel sleepLabel;
    private javax.swing.JTextArea textArea;
    // End of variables declaration//GEN-END:variables
}
