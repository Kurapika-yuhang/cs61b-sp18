public class NBody {
    /** NBody is a class that will actually run your simulation. */
    public static double readRadius(String fileName) {
        In in = new In(fileName);
        int n = in.readInt();
        double r = in.readDouble();
        return r;
    }

    public static Planet[] readPlanets(String fileName) {
        In in = new In(fileName);
        int n = in.readInt();
        double r = in.readDouble();

        Planet[] planets = new Planet[n];

        for (int i = 0; i < n; i++){
            double xp = in.readDouble();
            double yp = in.readDouble();
            double xv = in.readDouble();
            double yv = in.readDouble();
            double m = in.readDouble();
            String img = in.readString();

            planets[i] = new Planet(xp,yp,xv,yv,m,img);
        }
        return planets;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);
        Planet[] planets = readPlanets(filename);
        double t = 0.0;
        int n = planets.length;

        String bgImg = "images/starfield.jpg";

        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-radius,radius);

        while (t < T) {
            double[] xForces = new double[n];
            double[] yForces = new double[n];

            for (int i = 0; i < n; i++) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }

            for (int i = 0; i < n; i++){
                planets[i].update(dt,xForces[i],yForces[i]);
            }

            // draw bg
            StdDraw.clear();
            StdDraw.picture(0,0,bgImg);

            // draw planets
            for (Planet p:planets) {
                p.draw();
            }

            StdDraw.show();
            StdDraw.pause(10);
            t += dt;
        }

        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }
}
