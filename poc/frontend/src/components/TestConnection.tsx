export function TestConnection({ testConnection, connectivityStatus }: any) {
    
    return (
        <div className="container">
        <div className="row justify-content-md-center">
          <div className="col-12 text-center">
          
          </div>
          <div className="col-6 p-4 text-center">
             <p>Feel free to test connectivity.</p>
            <button
                className="btn btn-warning"
                type="button"
                onClick={()=>  testConnection()}>Ping</button>
            <div >{connectivityStatus}</div>
          </div>
        </div>
      </div>
    );
  }