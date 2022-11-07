package com.odh.socket2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class socket2 {
    public static String uploadPath = "D:/odh/socket2/";

    public static void main(String[] args) {
        // 1초 간격 실행 위해 Timer 사용
        Timer socket = new Timer();
        socket.schedule(new TimerTask() {
            @Override
            public void run() {
                receive(); // 소캣으로 받기
            }
        }, 0, 1000);
    }

    public static void receive() {
        try {
            // 8082 소켓서버 연결
            ServerSocket ss = new ServerSocket(8082);
            Socket socket = ss.accept(); // 연결이 될 때까지 대기

            // 데이터를 읽기 위해 DataInputStream 생성
            InputStream inputStream = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);

            // 데이터 읽기
            String message = dataInputStream.readUTF();
            save(message);

            // 파일 저장 후 닫기
            System.out.println("데이터 받기 완료"); // 통신 확인을 위한 sout
            ss.close();
            socket.close();
        }
        catch (IOException e){
            System.out.println("Error : " + e); // 에러난 경우 에러 띄우기
        }
    }

    public static void save(String title){
        try {
            // 경로가 없는 경우 경로 만들기
            File folder = new File(uploadPath);
            if (!folder.exists()) folder.mkdirs();

            // 파일 만들기
            OutputStream output = new FileOutputStream(uploadPath + title);
            String str = title.replace(".txt", "");
            byte[] by=str.getBytes();
            output.write(by);

        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
