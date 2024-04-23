package core.utils

import com.github.sarxos.webcam.Webcam
import core.domain.entities.Camera
import org.bytedeco.javacv.VideoInputFrameGrabber


object CameraHelper{
    fun listAvailableCameras(): List<String> {
        val cameras = mutableListOf<String>()
        val grabber = VideoInputFrameGrabber(0)
//        print(grabber.)
//        try {
//            grabber.start()
//            for (i in 0 until grabber.) {
//                cameras.add("Camera $i")
//            }
//            grabber.stop()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
        return cameras
    }

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
