package chatroom.client;

import java.io.*;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.ServerSocket;
import java.net.Socket;
/**
 *
 * @author lqshanshuo
 */
public class ChatClient {
    public static void main(String[] args){
        String hostName = "localhost";
        int portNumber = 12345;
        Socket socket=null;//����Socket�����socket
        try {
            try{
                socket = new Socket(hostName,portNumber);//�½�socket����
            }catch(Exception e){
                System.out.println("Connection failed!");
            }
            //��ȡ�û����������ַ���
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            //��ȡ����������ڿͻ�����������˷�������
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            //���ش����ı��ļ��洢��ʷ��¼
            String historyFileName;
            do{
            	//ǿ��ת��ΪString��
                historyFileName=""+(int)(Math.random()*100000);
            }while(new File("./history/"+historyFileName+".txt").exists());
            File f = new File("./history/"+historyFileName+".txt");
            //���Զ�ˢ��д���ļ�
            PrintWriter history = new PrintWriter(new FileWriter(f,true));

            try{
                new ReceiveThread(socket,history);//���������߳̽��շ�������Ϣ
            }catch(IOException e){
                socket.close();
                //�ر�socket��ʹ�ܹ��ɹ�ɾ��f�ļ�
                f.delete();
                System.out.println("Receive Thread start failed!This socket is closed.");
            }
            //����ѭ��������
            while(true){
                //��ȡ�û���������
                String str = userInput.readLine();
                try{sleep(1);}catch(Exception e){System.out.println(e.getMessage());}
                //��ʷ��¼����
                if(str.startsWith("/history")){
                    String[] strSplit=str.split(" +");
                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
                    String lineTxt = null;
                    int i=0,sign=0;
                    System.out.println("-------------history-------------");
                    if(strSplit.length==1){//�޲���ʱĬ����ʾ50����ʷ��¼
                        while((lineTxt = br.readLine()) != null){
                            i++;
                            if(i<=50){
                                System.out.println(i+"."+lineTxt);
                                sign=1;
                            }
                        }
                    }
                    if(strSplit.length==2){//��ѡ�������ӵڼ�����ʼ��ʾ
                        while((lineTxt = br.readLine()) != null){
                            i++;
                            if(i>=Integer.valueOf(strSplit[1])){
                                System.out.println(i+"."+lineTxt);
                                sign=1;
                            }
                        }
                    }
                    if(strSplit.length==3){//��ѡ������һ����ʾ������
                        while((lineTxt = br.readLine()) != null){
                            i++;
                            if(i>=(Integer.valueOf(strSplit[1]))+(Integer.valueOf(strSplit[2])))
                                break;
                            if(i>=Integer.valueOf(strSplit[1])){
                                System.out.println(i+"."+lineTxt);
                                sign=1;
                            }
                        }
                    }
                    if(sign==0)
                        System.out.println("No history meet the requirements");//������Ҫ����ʷ��¼
                    System.out.println("------------------------------------");
                }else{
                	//���������������
                    dos.writeUTF(str);
                    if("/quit".equals(str)){//�˳�
                        System.out.println("quit!!!!!!!!!!!!!!!");

                        f.delete();
                        break;
                    }
                }
            }
            socket.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}