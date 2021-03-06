/*
  This shows simple text floating around.
*/
package canvas

import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import kotlin.browser.document
import kotlin.browser.window
import kotlin.js.Math

val canvas ={
  val canvas = document.createElement("canvas") as HTMLCanvasElement
  val context = canvas.getContext("2d") as CanvasRenderingContext2D
  context.canvas.width  = window.innerWidth
  context.canvas.height = window.innerHeight
  document.body!!.appendChild(canvas)
  canvas
}()

val context=canvas.getContext("2d") as CanvasRenderingContext2D

val width=canvas.width

val height=canvas.height


// class representing a floating text
class Hello() {
  var relX = 0.2 + 0.2 * Math.random()
  var relY = 0.4 + 0.2 * Math.random()

  val absX: Double
    get() = (relX * width)
  val absY: Double
    get() = (relY * height)

  var relXVelocity = randomVelocity()
  var relYVelocity = randomVelocity()


  val message = "Hello, Kotlin!"
  val textHeightInPixels = 20
  init {
    context.font = "bold ${textHeightInPixels}px Georgia, serif"
  }
  val textWidthInPixels = context.measureText(message).width

  fun draw() {
    context.save()
    move()
    // if you using chrome chances are good you wont see the shadow
    context.shadowColor = "#000000"
    context.shadowBlur = 5.0
    context.shadowOffsetX = -4.0
    context.shadowOffsetY = 4.0
    context.fillStyle = "rgb(242,160,110)"
    context.fillText(message, absX, absY)
    context.restore()
  }

  fun move() {
    val relTextWidth = textWidthInPixels / width
    if (relX > (1.0 - relTextWidth - relXVelocity.abs) || relX < relXVelocity.abs) {
      relXVelocity *= -1
    }
    val relTextHeight = textHeightInPixels / height
    if (relY > (1.0 - relYVelocity.abs) || relY < relYVelocity.abs + relTextHeight) {
      relYVelocity *= -1
    }
    relX += relXVelocity
    relY += relYVelocity
  }

  fun randomVelocity() = 0.03 * Math.random() * (if (Math.random() < 0.5) 1 else -1)


  val Double.abs: Double
    get() = if (this > 0) this else -this
}

fun renderBackground() {
  context.save()
  context.fillStyle = "#5C7EED"
  context.fillRect(0.0, 0.0, width.toDouble(), height.toDouble())
  context.restore()
}

fun main(args: Array<String>) {
  val interval = 50
  // we pass a literal that constructs a new Hello object
  val logos = Array(3) {
    Hello()
  }

  window.setInterval({
    renderBackground()
    for (logo in logos) {
      logo.draw()
    }
  }, interval)
}


