<Query Kind="Program">
  <Namespace>System.Drawing</Namespace>
  <Namespace>System.Drawing.Imaging</Namespace>
</Query>

void Main()
{
	Image input = Image.FromFile(@"F:\Eigene Dateien\Dropbox\Programming\Java\workspace\Cannon Shooter\data\cannon_barrel_base.png");
	
	Image output = new Bitmap(input.Width * 4, input.Height * 8, PixelFormat.Format32bppArgb);

	using (Graphics g = Graphics.FromImage(output))
	{
		for (int x = 0; x < 4; x++)
		{
			for (int y = 0; y < 8; y++)
			{
				int idx = y*4 + x;

				g.DrawImageUnscaledAndClipped(input, new Rectangle(input.Width * x, input.Height * y, input.Width - idx * 8 - 58, input.Height));
				
				g.DrawImage(input, new Rectangle(input.Width * (x+1) - idx * 8 - 58, input.Height * y, 58, input.Height), new Rectangle(input.Width - 58, 0, 58, input.Height), GraphicsUnit.Pixel);
			}
		}
	}

	output.Dump();

	output.Save(@"F:\Eigene Dateien\Dropbox\Programming\Java\workspace\Cannon Shooter\android\assets\cannon_barrel.png");
}