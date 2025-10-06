# Complemento, Unión e Intersección

## 1. Contextualización

En la teoría de los **conjuntos difusos**, propuesta por *Lotfi Zadeh (1965)*, la pertenencia de un elemento a un conjunto no es binaria, sino que se expresa mediante un valor de pertenencia entre 0 y 1.

Esto permite representar conceptos imprecisos, como "números grandes" o "personas altas", de forma gradual.

En este apartado se desarrollan tres operaciones fundamentales sobre conjuntos difusos:

- **Complemento** $$ \bar{S} $$
- **Unión** $$ S_1 \cup S_2 $$
- **Intersección** $$ S_1 \cap S_2 $$

Cada una de ellas transforma o combina conjuntos difusos mediante funciones que operan sobre los valores de pertenencia $$ \mu(x) \in [0,1] $$.

---

## 2. Explicación de las partes del código

```scala
type ConjDifuso = Map[Int, Double]
```
Se define un tipo alias `ConjDifuso`, que representa un conjunto difuso como un **mapa de enteros a valores reales** (grado de pertenencia).

### 🔹 Complemento

```scala
def complemento(c: ConjDifuso): ConjDifuso = {
  c.map { case (elem, pertenencia) => (elem, 1.0 - pertenencia) }
}
```

**Descripción:**
Para cada elemento del conjunto difuso `c`, se calcula su complemento aplicando la ecuación:

$$
\mu_{\bar{S}}(x) = 1 - \mu_S(x)
$$

**Ejemplo:**
Si $$ c = \{(1, 0.2), (2, 0.8)\} $$, entonces:

$$
\bar{c} = \{(1, 0.8), (2, 0.2)\}
$$

---

### 🔹 Unión

```scala
def union(cd1: ConjDifuso, cd2: ConjDifuso): ConjDifuso = {
  val todasLasLlaves = cd1.keySet ++ cd2.keySet
  todasLasLlaves.map { k =>
    val val1 = cd1.getOrElse(k, 0.0)
    val val2 = cd2.getOrElse(k, 0.0)
    k -> Math.max(val1, val2)
  }.toMap
}
```

**Descripción:**
Se combinan los elementos de ambos conjuntos.  
Para cada elemento común o distinto se toma el **máximo grado de pertenencia**:

$$
\mu_{S_1 \cup S_2}(x) = \max(\mu_{S_1}(x), \mu_{S_2}(x))
$$

**Ejemplo:**

$$
A = \{(1, 0.2), (2, 0.7), (3, 1.0)\}, \quad
B = \{(2, 0.4), (3, 0.9), (4, 0.3)\}
$$

$$
A \cup B = \{(1, 0.2), (2, 0.7), (3, 1.0), (4, 0.3)\}
$$

---

### 🔹 Intersección

```scala
def interseccion(cd1: ConjDifuso, cd2: ConjDifuso): ConjDifuso = {
  val todasLasLlaves = cd1.keySet ++ cd2.keySet
  todasLasLlaves.map { k =>
    val val1 = cd1.getOrElse(k, 0.0)
    val val2 = cd2.getOrElse(k, 0.0)
    k -> Math.min(val1, val2)
  }.toMap
}
```

**Descripción:**
Para cada elemento, se toma el **mínimo grado de pertenencia** entre ambos conjuntos:

$$
\mu_{S_1 \cap S_2}(x) = \min(\mu_{S_1}(x), \mu_{S_2}(x))
$$

**Ejemplo:**

$$
A \cap B = \{(1, 0.0), (2, 0.4), (3, 0.9), (4, 0.0)\}
$$

---

## 3. Estado de la pila y operaciones

A continuación, se representa la ejecución interna mediante un **diagrama de pila de llamadas** (*call stack*) y notación matemática para cada función.

### 🧮 Complemento

$$
\text{Para cada elemento } (x, \mu(x)) \in C:
\quad \mu'(x) = 1 - \mu(x)
$$

```mermaid
graph TD
A[complemento(c)] --> B[map recorre c]
B --> C[(elem, pertenencia)]
C --> D[calcula 1 - pertenencia]
D --> E[retorna (elem, 1 - pertenencia)]
```

---

### 🧮 Unión

$$
\mu_{S_1 \cup S_2}(x) = \max(\mu_{S_1}(x), \mu_{S_2}(x))
$$

```mermaid
graph TD
A[union(cd1, cd2)] --> B[obtener llaves: cd1.keySet ++ cd2.keySet]
B --> C[para cada k en llaves]
C --> D[obtener val1 = cd1.getOrElse(k, 0.0)]
C --> E[obtener val2 = cd2.getOrElse(k, 0.0)]
D --> F[calcula Math.max(val1, val2)]
E --> F
F --> G[retorna (k -> resultado)]
```

---

### 🧮 Intersección

$$
\mu_{S_1 \cap S_2}(x) = \min(\mu_{S_1}(x), \mu_{S_2}(x))
$$

```mermaid
graph TD
A[interseccion(cd1, cd2)] --> B[obtener llaves: cd1.keySet ++ cd2.keySet]
B --> C[para cada k en llaves]
C --> D[obtener val1 = cd1.getOrElse(k, 0.0)]
C --> E[obtener val2 = cd2.getOrElse(k, 0.0)]
D --> F[calcula Math.min(val1, val2)]
E --> F
F --> G[retorna (k -> resultado)]
```

---

## 4. Argumentación de corrección

Demostramos que las funciones implementadas **son correctas** respecto a las definiciones matemáticas de la teoría de conjuntos difusos.

### 🔹 Complemento

**Postulado:**
$$
\forall x \in U, \quad \mu_{\bar{S}}(x) = 1 - \mu_S(x)
$$

**Argumento:**
El código aplica `1.0 - pertenencia` a cada par `(x, μ(x))`.  
Por tanto, la salida cumple exactamente con la definición matemática.

$$
\Rightarrow \text{Correcta por definición funcional de complemento.}
$$

---

### 🔹 Unión

**Postulado:**
\[
\forall x \in U, \quad \mu_{S_1 \cup S_2}(x) = \max(\mu_{S_1}(x), \mu_{S_2}(x))
\]

**Argumento:**
Para cada llave `k`, se calcula:

```scala
Math.max(val1, val2)
```

lo que corresponde directamente al operador \( \max \) en la definición matemática.  
Además, `getOrElse(k, 0.0)` asegura \( \mu(x) = 0 \) cuando el elemento no pertenece al conjunto.

$$
\Rightarrow \text{Correcta por equivalencia con la definición de unión difusa.}
$$

---

### 🔹 Intersección

**Postulado:**
$$
\forall x \in U, \quad \mu_{S_1 \cap S_2}(x) = \min(\mu_{S_1}(x), \mu_{S_2}(x))
$$

**Argumento:**
La función usa `Math.min(val1, val2)`, lo que implementa la definición teórica de intersección difusa.  
La unión de llaves \( cd1.keySet ++ cd2.keySet \) garantiza que ningún elemento del universo se omite.

$$
\Rightarrow \text{Correcta por definición de intersección difusa.}
$$

---

### ✅ Conclusión

Cada función cumple las propiedades de los operadores difusos:

$$
\begin{aligned}
&\text{Complemento:} & \mu_{\bar{S}}(x) = 1 - \mu_S(x) \\
&\text{Unión:} & \mu_{S_1 \cup S_2}(x) = \max(\mu_{S_1}(x), \mu_{S_2}(x)) \\
&\text{Intersección:} & \mu_{S_1 \cap S_2}(x) = \min(\mu_{S_1}(x), \mu_{S_2}(x))
\end{aligned}
$$

Por tanto, el código es **correcto funcional y matemáticamente**, y se ajusta a los principios de la **programación funcional pura** en Scala.
