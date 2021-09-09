package world;

import com.jme3.ai.navmesh.NavMesh;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.terrain.Terrain;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.geomipmap.lodcalc.DistanceLodCalculator;
import com.jme3.terrain.heightmap.HillHeightMap; // for exercise 2
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import java.util.ArrayList;
import java.util.List;
import telos.WorldManager;

/** Sample 10 - How to create fast-rendering terrains from heightmaps,
and how to use texture splatting to make the terrain look good.  */
public class HelloTerrain {

  private TerrainQuad terrain;
  Material mat_terrain;
  private int seed = 42;
  public NavMesh navMesh;
  
  public TerrainQuad getTerrain() { return terrain; }
      /**
     * Takes a Terrain, which can be composed of numerous meshes, and converts
     * them into a single mesh.
     * 
     * @param terr the terrain to be converted
     * @return a single mesh consisting of all meshes of a Terrain
     */
    public Mesh getTerrainMesh() {
        float[] heights = terrain.getHeightMap();
        int length = heights.length;
        int side = (int) FastMath.sqrt(heights.length);
        float[] vertices = new float[length * 3];
        int[] indices = new int[(side - 1) * (side - 1) * 6];

//        Vector3f trans = ((Node) terr).getWorldTranslation().clone();
        Vector3f trans = new Vector3f(0, 0, 0);
        trans.x -= terrain.getTerrainSize() / 2f;
        trans.z -= terrain.getTerrainSize() / 2f;
        float offsetX = trans.x;
        float offsetZ = trans.z;

        // do vertices
        int i = 0;
        for (int z = 0; z < side; z++) {
            for (int x = 0; x < side; x++) {
                vertices[i++] = x + offsetX;
                vertices[i++] = heights[z * side + x];
                vertices[i++] = z + offsetZ;
            }
        }

        // do indexes
        i = 0;
        for (int z = 0; z < side - 1; z++) {
            for (int x = 0; x < side - 1; x++) {
                // triangle 1
                indices[i++] = z * side + x;
                indices[i++] = (z + 1) * side + x;
                indices[i++] = (z + 1) * side + x + 1;
                // triangle 2
                indices[i++] = z * side + x;
                indices[i++] = (z + 1) * side + x + 1;
                indices[i++] = z * side + x + 1;
            }
        }

        Mesh mesh2 = new Mesh();
        mesh2.setBuffer(VertexBuffer.Type.Position, 3, vertices);
        mesh2.setBuffer(VertexBuffer.Type.Index, 3, indices);
        System.out.println("Doing " + side * side + " calcs");
        mesh2.updateBound();
        mesh2.updateCounts();

        return mesh2;
    }
  public HelloTerrain() {
    /** 1. Create terrain material and load four textures into it. */
    mat_terrain = new Material(WorldManager.assetManager,
            "Common/MatDefs/Terrain/Terrain.j3md");

    /** 1.1) Add ALPHA map (for red-blue-green coded splat textures) */
    mat_terrain.setTexture("Alpha", WorldManager.assetManager.loadTexture(
            "Textures/Terrain/splat/alphamap.png"));

    /** 1.2) Add GRASS texture into the red layer (Tex1). */
    Texture grass = WorldManager.assetManager.loadTexture(
            "Textures/Terrain/splat/grass.jpg");
    grass.setWrap(WrapMode.Repeat);
    mat_terrain.setTexture("Tex1", grass);
    mat_terrain.setFloat("Tex1Scale", 64f);

    /** 1.3) Add DIRT texture into the green layer (Tex2) */
    Texture dirt = WorldManager.assetManager.loadTexture(
            "Textures/Terrain/splat/dirt.jpg");
    dirt.setWrap(WrapMode.Repeat);
    mat_terrain.setTexture("Tex2", dirt);
    mat_terrain.setFloat("Tex2Scale", 32f);

    /** 1.4) Add ROAD texture into the blue layer (Tex3) */
    Texture rock = WorldManager.assetManager.loadTexture(
            "Textures/Terrain/splat/road.jpg");
    rock.setWrap(WrapMode.Repeat);
    mat_terrain.setTexture("Tex3", rock);
    mat_terrain.setFloat("Tex3Scale", 128f);

    /** 2. Create the height map */
    HillHeightMap heightmap = null;
    HillHeightMap.NORMALIZE_RANGE = 100;
    try {
        heightmap = new HillHeightMap(513, 1000, 50, 100, seed); // byte 3 is a random seed
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    heightmap.load();

    /** 3. We have prepared material and heightmap.
     * Now we create the actual terrain:
     * 3.1) Create a TerrainQuad and name it "my terrain".
     * 3.2) A good value for terrain tiles is 64x64 -- so we supply 64+1=65.
     * 3.3) We prepared a heightmap of size 512x512 -- so we supply 512+1=513.
     * 3.4) As LOD step scale we supply Vector3f(1,1,1).
     * 3.5) We supply the prepared heightmap itself.
     */
    int patchSize = 17;
    terrain = new TerrainQuad("my terrain", patchSize, 129, heightmap.getHeightMap());

    /** 4. We give the terrain its material, position & scale it, and attach it. */
    terrain.setMaterial(mat_terrain);
    terrain.setLocalTranslation(0, 0, 0);
    terrain.setLocalScale(1f, 1f, 1f);

    /** 5. The LOD (level of detail) depends on were the camera is: */
    TerrainLodControl control = new TerrainLodControl(terrain, WorldManager.cam);
    terrain.addControl(control);
    
    /** 6. collision */
    CollisionShape s = CollisionShapeFactory.createMeshShape(terrain);
    RigidBodyControl c = new RigidBodyControl(s, 0);
    terrain.addControl(c);
    System.out.println("Calcing navmesh");
    navMesh = new NavMesh(getTerrainMesh());
    
    WorldManager.root.attachChild(terrain);
    WorldManager.state.getPhysicsSpace().add(c);
    System.out.println("YOOOO");
  }
}