import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Hello2 {

  static class KPIHandler implements Runnable {
    private final PMData data;
    
    public KPIHandler(PMData data) {
      this.data = data;
    }

    public void run() {
      // Process KPI
      this.data.hashCode();
    }
  }

  public static class PMData {

  }

  public static void main(String[] args) {
    System.out.println("Hi");

    ExecutorService kpi = Executors.newFixedThreadPool(100);
    final PMData data = null;// ...
    kpi.execute(new KPIHandler(data));
  }
  
  private final BlockingQueue<PMData> kpiQueue = 
    new LinkedBlockingQueue<PMData>(100);
  
  public void dello2() {
    
    PMData data = null;
    try {
      kpiQueue.put(data);
    } catch (InterruptedException ex) {
      // what now???
    }


    new Thread() {
      
      public void run() {
        while (!interrupted()) {
          try {
            PMData data = kpiQueue.take();

            // process KPI data
            data.hashCode();

          } catch (InterruptedException ex) {
            break;
          }
        }
      }
      
    }.start();
    
    
    // Common code 
//    Context ctx = new InitialContext();
//    ConnectionFactory cf1 = (ConnectionFactory)
//        ctx.lookup("jms/QueueConnectionFactory");
//    Connection connection = connFactory.createConnection();
//    Session session = connection.createSession(false, 
//        Session.AUTO_ACKNOWLEDGE);
//    Destination dest = (Queue) ctx.lookup("jms/SomeQueue");
//    // Sender
//    MessageProducer producer = session.createProducer(dest);
//    // Receiver
//    connection.start();
//    MessageConsumer consumer = session.createConsumer(dest);
//    consumer.setMessageListener(listener);    
//    
//    
//    producer.send(message);
//    
//    KPIActor kpi = TypedActor.newInstance(KPIActor.class, KPIActorImpl.class);
//    
//    kpi.process(data);
//    
//    Actors.remote().start("localhost", 2552); 
//    Actors.remote().registerTypedActor("kpi", kpi);
//
//    KPIActor kpi = Actors.remote().typedActorFor(KPIActor.class, "kpi",
//        5000L, "localhost", 2552);
  }
  
  interface KPIActor {
    public void process(PMData data);
  }
  
  class KPIActorImpl implements KPIActor {
    @Override public void process(PMData data) {
    }
  }

}
