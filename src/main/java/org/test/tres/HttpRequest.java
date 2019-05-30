package org.test.tres;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

class HttpRequest{

	static String url="https://api.farelogix.com/xmlts/1cfe1af5";


	static String tc="<tc>\n" +
			"<iden u=\"TF3\" p=\"ieFeSEtE\" pseudocity=\"ALPC\" agt=\"xmltf001\" agtrole=\"Ticketing Agent\" agy=\"80208774\" agtpwd=\"Lt6Ds2Mtg44\"/>\n" +
			"<agent user=\"xmltf001\"/>\n" +
			"<trace>xmltf001</trace>\n" +
			"<script engine=\"FLXDM\" name=\"tf-dispatch.flxdm\"/>\n" +
			"</tc>";

	static 	String bodyrequest="<QueueRQ>\n" +
			"<List Source=\"F1\">\n" +
			"<QueueNumber>0</QueueNumber>\n" +
			"<PseudoCityCode>ALPC</PseudoCityCode>\n" +
			"<QueueCategory DateRange=\"1\"></QueueCategory>\n" +
			"</List>\n" +
			"</QueueRQ>";
	static String bodyrequest2="<PNRRetrieveRQ>\n" +
			"<RecordLocator>3SK7JT</RecordLocator>\n" +
			"</PNRRetrieveRQ>";

	public static void main(String[] args) {
/**
 *
 *
 *
 */
		 	String xml="<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
				"	<SOAP-ENV:Header>\n" +
				"		<t:Transaction xmlns:t=\"xxs\">\n" +
				tc+
				"		</t:Transaction>\n" +
				"	</SOAP-ENV:Header>\n" +
				"	<SOAP-ENV:Body>\n" +
				"		<ns1:XXTransaction xmlns:ns1=\"xxs\">\n" +
				"			<REQ>\n" +
				bodyrequest+
				"			</REQ>\n" +
				"		</ns1:XXTransaction>\n" +
				"	</SOAP-ENV:Body>\n" +
				"</SOAP-ENV:Envelope>";

		OkHttpClient client=new OkHttpClient().newBuilder().connectTimeout(60, TimeUnit.SECONDS).readTimeout(60,TimeUnit.SECONDS).build();
		okhttp3.RequestBody body=RequestBody.create(MediaType.get("application/json; charset=UTF-8"),xml);
		okhttp3.Request request=new Request.Builder().post(body).addHeader("Accept", "text/html,application/xhtml+xml,*/*").addHeader("Content-Type", "text/xml")
				.url(url)
				.build();
		Call newCall = client.newCall(request);
		System.out.println("request:");
		System.out.println(xml);
		newCall.enqueue(new Callback() {

			public void onResponse(Call arg0, Response arg1) throws IOException {
				System.out.println("response code: "+ arg1.code());

				ResponseBody body2 = arg1.body();

				InputStream byteStream = body2.byteStream();
//				String string2 = body2 .toString();

				String string = "/home/users/cody/myyyresult.xml";


				File file=new File(string);
				if(file.exists())
				{
					file.delete();
				}
					Files.copy(byteStream,file.toPath());

				arg1.close();
			}

			public void onFailure(Call arg0, IOException arg1) {
				arg1.printStackTrace();
				System.out.println( arg1.getMessage());

			}
		});
		client.dispatcher().executorService().shutdown();
	}

}