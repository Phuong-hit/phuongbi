package utils;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Lớp quản lý âm thanh
 */
public class SoundManager {

    private Map<String, Clip> clips;
    private boolean isMuted = false;

    // Lưu trữ âm lượng gốc để Mute/Unmute
    private Map<String, Float> originalVolumes = new HashMap<>();
    private static final float MUTE_VOLUME = -80.0f;

    public SoundManager() {
        clips = new HashMap<>();
    }

    public void loadSounds() {
        String[] soundNames = {
                "BallTap",
                "GameOverSound",
                "LevelCompleteSound",
                "ClickSound",
                "NhacNen"
        };

        for (String name : soundNames) {
            loadClip(name);
        }
        System.out.println("SoundManager: Đã tải xong " + clips.size() + " tệp âm thanh.");
    }

    private void loadClip(String name) {
        String path = "/sounds/" + name + ".wav";

        try {
            InputStream audioSrc = SoundManager.class.getResourceAsStream(path);
            if (audioSrc == null) {
                System.err.println("Không tìm thấy file âm thanh: " + path); return;
            }

            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(bufferedIn);

            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);

            //Xử lý Âm Lượng
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

                float volume = gainControl.getValue();

                if (name.equals("NhacNen")) {
                    float quieterVolume = Math.max(gainControl.getMinimum(), volume - 15.0f);
                    gainControl.setValue(quieterVolume);
                    originalVolumes.put(name, quieterVolume);
                } else {
                    originalVolumes.put(name, volume);
                }
            }


            clips.put(name, clip);

        } catch (Exception e) {
            System.err.println("Lỗi khi tải âm thanh: " + path + " - " + e.getMessage());
        }
    }

    public void setMuted(boolean muted) {
        this.isMuted = muted;
        for (String name : clips.keySet()) {
            Clip clip = clips.get(name);
            if (clip != null && clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                if (muted) {
                    gainControl.setValue(MUTE_VOLUME); // Tắt âm
                } else {
                    gainControl.setValue(originalVolumes.get(name)); // Trả về âm lượng gốc
                }
            }
        }

        // Tự động Bật/Tắt Nhạc Nền khi Mute/Unmute
        if (muted) {
            stopMusic("NhacNen");
        } else {
            playMusic("NhacNen");
        }
    }

    public void playMusic(String name) {
        if (isMuted) return;

        Clip clip = clips.get(name);
        if (clip != null) {
            if (!clip.isRunning()) {
                clip.setFramePosition(0);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        } else {
            System.err.println("Không tìm thấy clip nhạc nền: " + name);
        }
    }

    public void stopMusic(String name) {
        Clip clip = clips.get(name);
        if (clip != null) {
            clip.stop();
        }
    }

    public void play(String name) {
        if (isMuted) return;

        Clip clip = clips.get(name);
        if (clip != null) {
            clip.stop();
            clip.setFramePosition(0);
            clip.start();
        } else {
            System.err.println("Không tìm thấy clip SFX: " + name);
        }
    }
}