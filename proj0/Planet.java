public class Planet {
    private double xxPos;
    private double yyPos;
    private double xxVel;
    private double yyVel;
    private double mass;
    private String imgFileName;
    private static double G = 6.67e-11;

    public Planet(double xP, double yP, double xV,
                  double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet planet) {
        double dist = Math.sqrt(Math.pow(xxPos-planet.xxPos,2) + Math.pow(yyPos-planet.yyPos,2));
        return dist;
    }

    public double calcForceExertedBy(Planet planet) {
        double dist = calcDistance(planet);
        double F = G*mass*planet.mass/Math.pow(dist,2);
        return F;
    }

    public double calcForceExertedByX(Planet planet) {
        double F = calcForceExertedBy(planet);
        double dist = calcDistance(planet);
        double Fx = F*(planet.xxPos - xxPos)/dist;
        return Fx;
    }

    public double calcForceExertedByY(Planet planet) {
        double F = calcForceExertedBy(planet);
        double dist = calcDistance(planet);
        double Fy = F*(planet.yyPos - yyPos)/dist;
        return Fy;
    }

    public double calcNetForceExertedByX(Planet[] planets) {
        double netFx = 0;
        for (Planet p:planets) {
            if (p.equals(this)) {
                continue;
            }
            netFx += calcForceExertedByX(p);
        }
        return netFx;
    }

    public double calcNetForceExertedByY(Planet[] planets) {
        double netFy = 0;
        for (Planet p:planets) {
            if (p.equals(this)) {
                continue;
            }
            netFy += calcForceExertedByY(p);
        }
        return netFy;
    }

    public void update(double dt, double fX, double fY) {
        double ax = fX/mass;
        double ay = fY/mass;

        xxVel += dt*ax;
        yyVel += dt*ay;

        xxPos += dt*xxVel;
        yyPos += dt*yyVel;
    }

    public void draw() {
        StdDraw.picture(xxPos,yyPos,"images/"+imgFileName);
    }
}

