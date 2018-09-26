package top.shanbing.common.generateImg;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.AttributedString;

/**
 * @author shanbing
 * @date 2018/9/25.
 */
public class Graphics2DTest {

    public static void main(String[] args) throws Exception{
        File file1 = new File("C:\\Users\\shanbing\\Desktop\\123.png");
        File file2 = new File("C:\\Users\\shanbing\\Desktop\\1.png");

        BufferedImage backImage =  ImageIO.read(file1);

        // 图片的高/宽度
        int bwidth = backImage.getWidth();
        int bheight = backImage.getHeight();
        int alphaType = BufferedImage.TYPE_INT_RGB;
        // 画板图片
        BufferedImage backgroundImage = new BufferedImage(bwidth, bheight, alphaType);
        Graphics2D graphics2D = backgroundImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.drawImage(backImage.getScaledInstance(bwidth, bheight, Image.SCALE_SMOOTH), 0, 0, null);
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1));


        graphics2D.setColor( new Color(222, 48, 70));  //颜色
        AttributedString as = new AttributedString(new String("测试中文乱码".getBytes("utf-8")));
        as.addAttribute(TextAttribute.FONT, new Font("仿宋", Font.BOLD, 88));
        graphics2D.drawString(as.getIterator(), 10, 100);



        graphics2D.dispose();
        ImageIO.write(backgroundImage, "png",file2);
    }
}
