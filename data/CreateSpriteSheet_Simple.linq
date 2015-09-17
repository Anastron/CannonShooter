<Query Kind="Program">
  <Namespace>System.Drawing</Namespace>
  <Namespace>System.Drawing.Imaging</Namespace>
</Query>

void Main()
{
	Image input = Image.FromFile(@"F:\Eigene Dateien\Dropbox\Programming\Java\workspace\Cannon Shooter\data\cannon_barrel_single.png");
	
	Image output = new Bitmap(input.Width * 4, input.Height * 4, PixelFormat.Format32bppArgb);

	using (Graphics g = Graphics.FromImage(output))
	{
		for (int x = 0; x < 4; x++)
		{
			for (int y = 0; y < 4; y++)
			{
				int idx = y*4 + x;

				g.DrawImageUnscaledAndClipped(input, new Rectangle(input.Width * x, input.Height * y, input.Width - idx * 8 - 22, input.Height));
				
				g.DrawImage(input, new Rectangle(input.Width * (x+1) - idx * 8 - 22, input.Height * y, 22, input.Height), new Rectangle(input.Width - 22, 0, 22, input.Height), GraphicsUnit.Pixel);
			}
		}
	}

	output.Dump();

	output.Save(@"F:\Eigene Dateien\Dropbox\Programming\Java\workspace\Cannon Shooter\android\assets\cannon_barrel.png");
}