package taller

class ConjuntosDifusos {
  // ---CONJUNTO DIFUSO PARA NUMEROS GRANDES---

  type ConjDifuso = Map[Int, Double] //se uso Map aqui para despues implementarlo
                                    // como un acumulador

  def identificadorN(n: Int, d: Int, e: Int): Double =
    Math.pow(n.toDouble / (n + d).toDouble, e.toDouble) //se uso la biblioteca Math en este caso

  def grande(d: Int, e: Int): ConjDifuso = {
    require(d >= 1 && e > 1)

    val cantInicial = 1
    val cantFinal = 10000

    @annotation.tailrec
    def inicializador(n: Int, acc: ConjDifuso, f: (Int, Int, Int) => Double): ConjDifuso = {
      if (n > cantFinal) acc
      else {
        val valor = f(n, d, e)
        inicializador(n + 1, acc + (n -> valor), f)
      }
    }

    inicializador(cantInicial, Map.empty, identificadorN)
  }
    def complemento(c: ConjDifuso): ConjDifuso = {
      c.map { case (elem, pertenencia) => (elem, 1.0 - pertenencia) }
    }

    def union(cd1: ConjDifuso, cd2: ConjDifuso): ConjDifuso = {
      // Se unen las llaves de ambos mapas para asegurar que se consideran todos los elementos.
      val todasLasLlaves = cd1.keySet ++ cd2.keySet
      todasLasLlaves.map { k =>
        val val1 = cd1.getOrElse(k, 0.0) // Usar 0.0 si la llave no existe en un mapa
        val val2 = cd2.getOrElse(k, 0.0)
        k -> Math.max(val1, val2)
      }.toMap
    }
    def interseccion(cd1: ConjDifuso, cd2: ConjDifuso): ConjDifuso = {
      val todasLasLlaves = cd1.keySet ++ cd2.keySet
      todasLasLlaves.map { k =>
        val val1 = cd1.getOrElse(k, 0.0)
        val val2 = cd2.getOrElse(k, 0.0)
        k -> Math.min(val1, val2)
      }.toMap
    }
}
