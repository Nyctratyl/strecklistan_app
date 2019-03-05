package com.example.streck_app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

public class HTTP {

    public static String doRequest(final String page, final String params) {
        //Cheap hack to be able to return vals from inside the thread
        final String[] response = new String[1];
        final CountDownLatch latch = new CountDownLatch(1);

        Thread req_thread = new Thread(new Runnable() {
            @Override
                    public void run() {
        try

            {
                /* Code stolen from StackOverflow and meagerly modified starts here */

                String USER_AGENT = "Mozilla/5.0";

                String POST_URL = "http://pq.duckdns.org/streck/" + page;

                URL obj = new URL(POST_URL);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("User-Agent", USER_AGENT);

                con.setDoOutput(true);
                OutputStream os = con.getOutputStream();
                os.write(params.getBytes());
                os.flush();
                os.close();

                InputStreamReader in = new InputStreamReader(con.getInputStream());
                BufferedReader br = new BufferedReader(in);
                response[0] = br.readLine();
                System.out.println(response[0]);
                latch.countDown(); // Release await() in the test thread.

                /* Code stolen from StackOverflow and meagerly modified starts here */
            } catch(IOException e) {System.out.println(e);}

        }});
        req_thread.start();

        try {
            latch.await();
        }
        catch (InterruptedException e) {
            System.out.println(e);
        }
        return response[0];

    }

}
