# 1. Contextualización

Se busca cumplir el parametro de realizar un codigo
basado en conjuntos difusos con la habilidad de discernir
entre números grandes mediante dos variables. Donde **d**
es número mayor o igual a **1**, y donde posteriormente
**n** es otro número cualquiera, si este es grande,
mediante la operación **(n + d)** se tendria que devolver
un valor cercano a **1** una vez que se ensambla la operación:

$$
\
\left(\frac{n}{n+d}\right)^{e}
\
$$

Adicionalmente, al tener como exponente **euler** este nos permite
incrementer el grado de pertenencia, con esto en cuenta, se
propone el siguiente codigo:

```scala
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
}
//-------------------------------------------------------------
```
## 1.1) Class ConjuntosDifusos

Analizando a mayor profundidad el codigo, primero se define
la clase además usar **Map** con valores enteros y doubles (decimales) para
ayudar encontrar el grado de pertenencia:

```scala
class ConjuntosDifusos {
  // ---CONJUNTO DIFUSO PARA NUMEROS GRANDES---

  type ConjDifuso = Map[Int, Double] //se uso Map aqui para despues implementarlo
                                    // como un acumulador
```

## 1.2) Identificador

Prosiguiendo, tenemos el identificador que hace uso de nuestra
operación representada en Latex que se menciono previamente:

```scala
def identificadorN(n: Int, d: Int, e: Int): Double =
    Math.pow(n.toDouble / (n + d).toDouble, e.toDouble) //se uso la biblioteca Math en este caso
```

## 1.3) Parametros de las variables (d,n)

Posteriormente, se tiene una representación de los
parametros que las variables **d** y **n** deben cumplir
para el funcionamiento del codigo ademas del exponente 
euler definiendose mayor que 1:

```scala
  def grande(d: Int, e: Int): ConjDifuso = {
    require(d >= 1 && e > 1)

    val cantInicial = 1
    val cantFinal = 10000
```

De igual manera, se expresan el tamaño de los números con los 
que trabajara la función.

## 1.4) Inicializador

Despues tenemos el inicializador que se encargara de 
realizar una recursión de cola mediante las iteraciones del 
codigo al llamar el identificador, teniendo un acumulador que 
crece junto al **Map**:

```scala
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
}
```
Finalmente, se realiza el ultimo llamado para ejecutar el codigo 
respecto a los valores de inicio y fin que le hemos otorgado.

# 2. Estado de pila

Debido a que la recursividad hace que la pila se mantenga unica 
pero actualizando sus valores con el paso del tiempo, un ejemplo de
como se veria su funcionamiento se propone:

### - Estado 1:

```scala
inicializador(1, Map())
```

Suponiendo que una vez llamado el identificador, los valores de **n** seran
10 y el exponente **euler** 2, por ende su representación grafica
se veria de la siguiente manera:

$$
\
\left(\frac{1}{1+10}\right)^{2}
\
$$

Este proceso nos dara un resultado de **0.00826** que sera
atribuido al **Map** para llevar una cuenta como su acumulador. 
El valor de **n** también ira aumentado junto al acumulador.

### -Estado 2:

```scala
inicializador(2, Map(1 --> 0.00826))
```

El mismo proceso se repetira por cada incremento de **n** al ejecutarse
la operación planteada hasta que pare en la cantidad final
atribuida, osea, 10000.

### -Estado 3:

```scala
inicializador(3, Map(1 --> 0.00826, 2 --> 0.0277))
```

Cuando se llegue al valor esperado, el estado de la pila se
veria tal que asi:

### -Estado 10000:

```scala
inicializador(10000, Map(1 --> ....., 10000 --> 0.998))
```

# 3. Grafica Mermaid

(Pendiente)