public class wait {
    public static void main(String[] args) {
        WaitNotifySimple wns = new WaitNotifySimple();
        new Thread (wns).start();
        new Thread (wns).start();
    }
    
    static class WaitNotifySimple implements Runnable{
        
        private volatile boolean ejecutandoMetodo1 = false;

        public synchronized void metodo1(){

            for (int i=0; i<10; i++){
                System.out.printf(Thread.currentThread().getName() + " Ejecución %d\n", i);
                if (i == 5) {
                    try{
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();;
                    }
                }
            }

        }
    
        public synchronized void metodo2(){

            for (int i = 0; i < 20; i++) {
                System.out.printf(Thread.currentThread().getName() + " Ejecución %d\n", i);
            }
            this.notifyAll();

        }

        @Override
        public void run(){
            if (!ejecutandoMetodo1){
                ejecutandoMetodo1 = true;
                metodo1();
            } else {
                metodo2();
            }
        }
    }
}