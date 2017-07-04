package chatroom.client;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

/**
 *
 * @author lqshanshuo
 */
public class ChatroomJFrame extends javax.swing.JFrame {

    File f=null;
    DataOutputStream dos=null;
    DataInputStream dis=null;
    Socket socket=null;
    public void connect(){
        String hostName = "localhost";
        int portNumber = 12345;
        try {
            try{
                socket = new Socket(hostName,portNumber);
            }catch(Exception e){
                System.out.println("Connection failed!");
            }

            //获取输出流，用于客户端向服务器端发送数据
            dos = new DataOutputStream(socket.getOutputStream());
            //获取输入流，用于接收服务器端发送来的数据
            dis = new DataInputStream(socket.getInputStream());
            String historyFileName;
            do{
                historyFileName=""+(int)(Math.random()*100000);
            }while(new File("./history/"+historyFileName+".txt").exists());
            f = new File("./history/"+historyFileName+".txt");
            PrintWriter history = new PrintWriter(new FileWriter(f,true));

            try{
                new ReceiveThread1(this,socket,history);
            }catch(IOException e){
                socket.close();
                f.delete();
                System.out.println("Receive Thread start failed!This socket is closed.");
            }

        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    public int send(String str){
        //try{sleep(1);}catch(Exception e){System.out.println(e.getMessage());}
                if(str.startsWith("/history")){
                    try{
                    String[] strSplit=str.split(" +");
                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
                    String lineTxt = null;
                    int i=0,sign=0;
                    jTextArea1.append("-------------history-------------"+"\n");
                    if(strSplit.length==1){
                        while((lineTxt = br.readLine()) != null){
                            i++;
                            if(i<=50){
                                jTextArea1.append(i+"."+lineTxt+"\n");
                                sign=1;
                            }
                        }
                    }
                    if(strSplit.length==2){
                        while((lineTxt = br.readLine()) != null){
                            i++;
                            if(i>=Integer.valueOf(strSplit[1])){
                                jTextArea1.append(i+"."+lineTxt+"\n");
                                sign=1;
                            }
                        }
                    }
                    if(strSplit.length==3){
                        while((lineTxt = br.readLine()) != null){
                            i++;
                            if(i>=(Integer.valueOf(strSplit[1]))+(Integer.valueOf(strSplit[2])))
                                break;
                            if(i>=Integer.valueOf(strSplit[1])){
                                jTextArea1.append(i+"."+lineTxt+"\n");
                                sign=1;
                            }
                        }
                    }
                    if(sign==0)
                    jTextArea1.append("No history meet the requirements"+"\n");
                    jTextArea1.append("------------------------------------"+"\n");
                    }catch(IOException e){System.out.println(e.getMessage());}
                }else{
                    try{
                    dos.writeUTF(str);}catch(IOException e){System.out.println(e.getMessage());}
                    if("/quit".equals(str)){
                        jTextArea1.append("quit!!!!!!!!!!!!!!!"+"\n");

                        f.delete();
                        return 0;
                    }
                }
                return 1;
    }
    public void receive(String str){
        jTextArea1.append(str+"\n");
        jTextArea1.setCaretPosition(jTextArea1.getText().length());
    }

    public ChatroomJFrame() {
        initComponents();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ChatRoom");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jButton1.setText(" send ");
        jButton1.setToolTipText("");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("Greet");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Angry");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("Goodbye");
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton4);
        jRadioButton4.setSelected(true);
        jRadioButton4.setText("Null");
        jRadioButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton4ActionPerformed(evt);
            }
        });

        jButton2.setText("help");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton3)
                    .addComponent(jRadioButton4))
                .addGap(0, 8, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addGap(0, 84, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                    .addComponent(jTextField1))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        String str = jTextField1.getText();
        jTextArea1.append(str+"\n");
        jTextField1.setText("");
        if(send(str)==0)
            try{
                socket.close();
            }catch(IOException e){System.out.println(e.getMessage());}
    }                                        

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        String str = jTextField1.getText();
        jTextArea1.append(str+"\n");
        jTextField1.setText("");
        if(send(str)==0)
            try{
                socket.close();
            }catch(IOException e){System.out.println(e.getMessage());}
    }                                           

    private void formWindowClosing(java.awt.event.WindowEvent evt) {                                   
        f.delete();
        send("/quit");
    }                                  

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                              
        jTextField1.setText("//Angry! @");
    }                                             

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                              
        jTextField1.setText("//Greet! @");
    }                                             

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {                                              
        jTextField1.setText("//Goodbye! @");
    }                                             

    private void jRadioButton4ActionPerformed(java.awt.event.ActionEvent evt) {                                              
        jTextField1.setText("");
    }                                             

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        JDialog dialog = new JDialog();
       dialog.setTitle("help");
       dialog.setSize(450,170);

        Container contentPane = dialog.getContentPane();
       contentPane.add(new JLabel(
               "<html>/login xxx: login and use 'xxx' as username.<br/>"
                       + "/who &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp : show all online users.<br/>"
                       + "/history &nbsp : show history.<br/>"
                       + "/quit &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp : quit this chatroom.<br/><br/>"
                       + "click on the right RadioButton for send emotins or send '//XXX@xxx'.<html>"
               ,JLabel.CENTER),BorderLayout.CENTER);
       dialog.setVisible(true);
    }                                        

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
            java.util.logging.Logger.getLogger(ChatroomJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatroomJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatroomJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatroomJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        final ChatroomJFrame chatroomJFrame=new ChatroomJFrame();

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                chatroomJFrame.setVisible(true);
            }
        });
        chatroomJFrame.jPanel1.setVisible(true);
        chatroomJFrame.connect();
    }

    // Variables declaration - do not modify                     
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration                   
}