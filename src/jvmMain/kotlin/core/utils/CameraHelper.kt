package core.utils

import com.github.sarxos.webcam.Webcam
import core.domain.entities.Camera
import org.bytedeco.javacv.VideoInputFrameGrabber


object CameraHelper{
//    @JvmStatic
    fun getAvailableCameras() : List<Camera> {
        val cameras: MutableList<Camera> = mutableListOf()
        val list = Webcam.getWebcams()
        for (i in list.indices) {
            try {
                val cam = list[i]
                println("Found this Camera : " + cam.name)
//                val image = cam.image
                cameras.add(Camera(camIndex = i, name = cam.name))
            } catch (e: Exception) {
                println("Exception in cam : $i")
            }
        }
        return cameras
    }
}
