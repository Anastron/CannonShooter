<Query Kind="Program">
  <Reference>&lt;RuntimeDirectory&gt;\System.Numerics.dll</Reference>
  <Namespace>System.Drawing</Namespace>
  <Namespace>System.Drawing.Imaging</Namespace>
  <Namespace>System.Numerics</Namespace>
</Query>

void Main()
{
	var input = new Bitmap(Image.FromFile(@"F:\Eigene Dateien\Dropbox\Programming\Java\workspace\Cannon Shooter\data\cannon_hearth_base.png"));
	
	Image output = new Bitmap(input.Width * 8, input.Height * 8, PixelFormat.Format32bppArgb);

	var b1 = new SolidBrush(Color.Transparent);
	var b2 = new SolidBrush(Color.White);

	using (Graphics g = Graphics.FromImage(output))
	{
		for (int x = 0; x < 8; x++)
		{
			for (int y = 0; y < 8; y++)
			{
				var rad = ((y*8 + x) / 63.0) * 2 * Math.PI;

				for (int ix = 0; ix < input.Width; ix++)
				{
					for (int iy = 0; iy < input.Height; iy++)
					{
						int dx = input.Width * x + ix;
						int dy = input.Height * y + iy;

						if (input.GetPixel(ix, iy).A > 0)
						{
							if (GetAngle(new Vector2(ix - input.Width / 2f, iy - input.Height / 2f)) >= rad)
							{
								g.FillRectangle(b1, dx, dy, 1, 1);
							}
							else
							{
								g.FillRectangle(b2, dx, dy, 1, 1);

							}
						}
					}
				}
			}
		}
	}

	output.Dump();

	output.Save(@"F:\Eigene Dateien\Dropbox\Programming\Java\workspace\Cannon Shooter\android\assets\cannon_hearth.png");
}

double GetAngle(Vector2 p)
{
	var vector2 = p;
	var vector1 = new Vector2(0, -1);

	return (Math.Atan2(vector2.Y, vector2.X) - Math.Atan2(vector1.Y, vector1.X) + 2*Math.PI) % (2*Math.PI);
}