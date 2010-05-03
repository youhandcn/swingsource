package sweepmine;

import javax.swing.ImageIcon;

public interface CodeConstants {
	/**
	 * 定义xx的难度级别
	 */
	public enum GameLevel {
		/** 简单 */
		EASY,
		/** 普通 */
		NORMAL,
		/** 困难 */
		HARD,
		/** 疯狂 */
		CRAZY;
	}

	/**
	 * 定义四周环绕的xx的数目
	 */
	public enum AroundNumCount {
		ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE;
	}

	/**
	 * 定义时间数字的图片
	 */
	public enum TimeNumberImage {
		/** 数字0图片 */
		IMAGE0(ImageManager.loadImage("/resources/timenumber/number0.gif")),
		/** 数字1图片 */
		IMAGE1(ImageManager.loadImage("/resources/timenumber/number1.gif")),
		/** 数字2图片 */
		IMAGE2(ImageManager.loadImage("/resources/timenumber/number2.gif")),
		/** 数字3图片 */
		IMAGE3(ImageManager.loadImage("/resources/timenumber/number3.gif")),
		/** 数字4图片 */
		IMAGE4(ImageManager.loadImage("/resources/timenumber/number4.gif")),
		/** 数字5图片 */
		IMAGE5(ImageManager.loadImage("/resources/timenumber/number5.gif")),
		/** 数字6图片 */
		IMAGE6(ImageManager.loadImage("/resources/timenumber/number6.gif")),
		/** 数字7图片 */
		IMAGE7(ImageManager.loadImage("/resources/timenumber/number7.gif")),
		/** 数字8图片 */
		IMAGE8(ImageManager.loadImage("/resources/timenumber/number8.gif")),
		/** 数字9图片 */
		IMAGE9(ImageManager.loadImage("/resources/timenumber/number9.gif"));

		private ImageIcon imageIcon = null;

		TimeNumberImage(ImageIcon imageIcon) {
			this.imageIcon = imageIcon;
		}

		public ImageIcon getImageIcon() {
			return this.imageIcon;
		}
	}
	
	/**
	 * 定义xx数字的图片
	 */
	public enum MineNumberImage {
		/** 数字0图片 */
		IMAGE0(ImageManager.loadImage("/resources/minenumber/number0.gif")),
		/** 数字1图片 */
		IMAGE1(ImageManager.loadImage("/resources/minenumber/number1.gif")),
		/** 数字2图片 */
		IMAGE2(ImageManager.loadImage("/resources/minenumber/number2.gif")),
		/** 数字3图片 */
		IMAGE3(ImageManager.loadImage("/resources/minenumber/number3.gif")),
		/** 数字4图片 */
		IMAGE4(ImageManager.loadImage("/resources/minenumber/number4.gif")),
		/** 数字5图片 */
		IMAGE5(ImageManager.loadImage("/resources/minenumber/number5.gif")),
		/** 数字6图片 */
		IMAGE6(ImageManager.loadImage("/resources/minenumber/number6.gif")),
		/** 数字7图片 */
		IMAGE7(ImageManager.loadImage("/resources/minenumber/number7.gif")),
		/** 数字8图片 */
		IMAGE8(ImageManager.loadImage("/resources/minenumber/number8.gif"));

		private ImageIcon imageIcon = null;

		MineNumberImage(ImageIcon imageIcon) {
			this.imageIcon = imageIcon;
		}

		public ImageIcon getImageIcon() {
			return this.imageIcon;
		}
	}
}
