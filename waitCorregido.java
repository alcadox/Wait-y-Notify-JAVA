public class waitCorregido {
    public static void main(String[] args) {
        WaitNotifySimple wns = new WaitNotifySimple();
        new Thread(wns, "Hilo-1").start();
        new Thread(wns, "Hilo-2").start();
    }

    static class WaitNotifySimple implements Runnable {

        private boolean metodo1Ejecutando = false;
        private boolean metodo1Terminado = false;

        public synchronized void metodo1() {
            metodo1Ejecutando = true;

            for (int i = 0; i < 10; i++) {
                System.out.printf(Thread.currentThread().getName() + " Ejecución %d\n", i);

                if (i == 5) {
                    try {
                        // esperamos a que metodo2 haga notifyAll
                        while (!metodo1Terminado) {
                            wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public synchronized void metodo2() {
            // esperamos a que metodo1 haya empezado
            while (!metodo1Ejecutando) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for (int i = 0; i < 20; i++) {
                System.out.printf(Thread.currentThread().getName() + " Ejecución %d\n", i);
            }

            // Avisamos a metodo1 que puede continuar
            metodo1Terminado = true;
            notifyAll();
        }

        @Override
        public void run() {
            if (!metodo1Ejecutando) {
                metodo1();
            } else {
                metodo2();
            }
        }
    }
}
